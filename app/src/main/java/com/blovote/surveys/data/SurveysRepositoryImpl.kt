package com.blovote.surveys.data

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.blovote.account.data.AccountStorage
import com.blovote.contracts.Contracts_BloGodImpl_sol_BloGodImpl
import com.blovote.contracts.Contracts_BlovoteImpl_sol_BlovoteImpl
import com.blovote.surveys.data.entities.*
import com.blovote.surveys.domain.SurveysRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.web3j.protocol.Web3j
import org.web3j.tx.Contract
import java.math.BigInteger
import java.nio.charset.Charset

class SurveysRepositoryImpl(private val surveysStorage: SurveysStorage,
                            private val web3j : Web3j,
                            private val godContractAddress : String,
                            private val accountStorage: AccountStorage) : SurveysRepository {

    private val gasPrice by lazy { web3j.ethGasPrice().send().gasPrice.multiply(BigInteger.valueOf(5L)) }

    private val contractBloGodImpl by lazy { Contracts_BloGodImpl_sol_BloGodImpl.load(
            godContractAddress, web3j, accountStorage.getCredentials(), gasPrice, Contract.GAS_LIMIT) }

    override fun getAllSurveys(): List<Survey> {
        return surveysStorage.getAllSurveys().value ?: listOf()
    }

    override fun observeAllSurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>> {
        return Observable.create<List<Survey>> {
            surveysStorage.getAllSurveys().observe(lifecycleOwner,
                    Observer<List<Survey>> { t -> it.onNext(t!!) })
        }
    }

    override fun observeCreatorsQuestions(lifecycleOwner: LifecycleOwner, address: String): Observable<List<Survey>> {
        return Observable.create<List<Survey>> {
            surveysStorage.getCreatorsSurveys(address)
                    .observe(lifecycleOwner, Observer<List<Survey>> { t -> it.onNext(t!!) })
        }
    }

    override fun updateSurveys(): List<Survey> {
        val surveysNumder = contractBloGodImpl.surveysNumber.send()
        val lastIndex = surveysStorage.getLastSurveyIndex()

        val newAddresses = contractBloGodImpl.getBlovoteAddresses(BigInteger.valueOf(lastIndex + 1L),
                surveysNumder).send()

        val list = mutableListOf<Survey>()
        for (i in newAddresses.indices) {
            val survey = loadBlovote(newAddresses[i].toString(), lastIndex + 1 + i)
            list.add(survey)
            surveysStorage.saveSurvey(survey)
        }

        return list
    }

    override fun updateSurveyQuestionInfo(survey: Survey, category: QuestionCategory, index: Int): Single<Survey> {
        return Single.create {
            try {
                it.onSuccess(loadQuestion(survey, category, index))
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun createSurvey(title: String,
                        requiredRespondentsCount: Int,
                        rewardSize: BigInteger,
                        filterQuestions : List<Question>,
                        mainQuestions: List<Question>) {

        val prevSurveysNumber = contractBloGodImpl.surveysNumber.send()
        contractBloGodImpl.createNewSurvey(title.toByteArray(Charset.forName("UTF-8")),
                BigInteger.valueOf(requiredRespondentsCount.toLong()),
                rewardSize.multiply(BigInteger.valueOf(requiredRespondentsCount.toLong()))).send()
        val currSurveysNumber = contractBloGodImpl.surveysNumber.send()
        val surveyContract = getBlovoteContract(contractBloGodImpl.getBlovoteAddresses(prevSurveysNumber, currSurveysNumber).send()[0].toString())

        for (i in 0 until filterQuestions.size) {
            val question = filterQuestions[i]

            surveyContract.addFilterQuestion(BigInteger.valueOf(question.type.getContractQuestionType().toLong()),
                    question.title.toByteArray(Charset.forName("UTF-8"))).send()

            for (j in 0 until question.points.size) {
                surveyContract.addFilterQuestionPoint(BigInteger.valueOf(i.toLong()),
                            question.points[j].toByteArray(Charset.forName("UTF-8")),
                        question.answers.contains(j)).send()
            }
        }

        for (i in 0 until mainQuestions.size) {
            val question = mainQuestions[i]

            surveyContract.addQuestion(BigInteger.valueOf(question.type.getContractQuestionType().toLong()),
                    question.title.toByteArray(Charset.forName("UTF-8"))).send()

            for (j in 0 until question.points.size) {
                surveyContract.addQuestionPoint(BigInteger.valueOf(i.toLong()),
                            question.points[j].toByteArray(Charset.forName("UTF-8")))
                        .send()

            }
        }

        surveyContract.updateState(BigInteger.valueOf(SurveyState.Active.ordinal.toLong())).send()
    }


    override fun getSurvey(address: String): Single<Survey> {
        return Single.create {
            val survey = surveysStorage.getSurvey(address)
            if (survey != null) {
                it.onSuccess(survey)
            } else {
                try {
                    //TODO: test with survey external link
                    it.onSuccess(loadBlovote(address, -1))
                } catch (e: Exception) {
                    e.printStackTrace()
                    it.onError(e)
                }
            }
        }
    }

    override fun updateSurveyInfo(survey: Survey) : Single<Survey> {
        return Single.create {
            try {
                val newSurvey = loadBlovote(survey.address, survey.index)
                surveysStorage.saveSurvey(newSurvey)
                it.onSuccess(newSurvey)
            } catch (e: Exception) {
                e.printStackTrace()
                it.onError(e)
            }
        }
    }

    override fun checkAnswer(survey: Survey, questionIndex: Int, answers: List<String>) : Single<Boolean> {
        return Single.create({
            try {
                val question = survey.filterQuesions[questionIndex]
                val rightAnswers = question.answers.map { it.toString() }
                val right = answers.size == rightAnswers.size &&
                        answers.all { rightAnswers.contains(it) }

                it.onSuccess(right)

            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }

    override fun uploadAnswer(survey: Survey, questionIndex: Int, answers: List<String>): Completable {
        return Completable.create({
            try {
                val blovote = getBlovoteContract(survey.address)
                val question = survey.questions[questionIndex]

                val receipt = if (question.type == QuestionType.Text) {
                    blovote.respondText(answers[0].toByteArray(Charset.forName("UTF-8"))).send()
                } else {
                    blovote.respondNumbers(answers.map { it -> it.toBigInteger() }).send()
                }

                it.onComplete()

            } catch (e: Exception) {
                e.printStackTrace()
                it.onError(e)
            }
        })
    }


    override fun loadRespondInfo(surveyAddress: String, index: Int): Completable {
        return Completable.create {
            try {
                val blovote = getBlovoteContract(surveyAddress)
                val questionsCount = blovote.questionsCount.send().toInt()

                val data : MutableList<Answers> = ArrayList()
                for (i in 0 until questionsCount) {
                    val answersArray = blovote.getRespondData(i.toBigInteger(), index.toBigInteger()).send()
                    val questionInfo = blovote.getQuestionInfo(i.toBigInteger()).send()
                    val type = QuestionType.fromContractQuestionType(questionInfo.value1)

                    val answer = if (type == QuestionType.Text) {
                        listOf(answersArray.toString(Charset.forName("UTF-8")))
                    } else {
                        answersArray.map { it.toString() }
                    }

                    data.add(Answers(answer))
                }

                surveysStorage.saveRespond(surveyAddress, index, data)
                it.onComplete()

            } catch (e: Exception) {
                it.onError(e)
            }
        }

    }

    override fun getResponds(lifecycleOwner: LifecycleOwner, surveyAddress: String): Observable<List<Respond>> {
        return Observable.create {
            surveysStorage.getResponds(surveyAddress).observe(lifecycleOwner,
                    Observer<List<Respond>> { r -> it.onNext(r!!) })
        }
    }


    private fun loadBlovote(address : String, index : Int) : Survey {
        val prevSurvey = surveysStorage.getSurvey(address)

        val blovote = getBlovoteContract(address)
        val title = blovote.title().send().toString(Charset.forName("UTF-8"))

        // creator field was added later and may not be presented at old surveys
        val creator = try {
            blovote.creator().send()
        } catch (e: Exception) { "" }

        val state = SurveyState.values()[blovote.state.send().toInt()]
        val creationTime = blovote.creationTimestamp().send().toLong()
        val requiredRespCnt = blovote.requiredRespondentsCount().send().toInt()
        val currentRespCnt = blovote.currentRespondentsCount().send().toInt()
        val rewardSize  = blovote.rewardSize().send().toString()
        val filterQuestionsCount = blovote.filterQuestionsCount.send().toInt()
        val questionsCount = blovote.questionsCount.send().toInt()

        val survey = Survey(address, index, creator, title, state, creationTime, requiredRespCnt, currentRespCnt,
                rewardSize, filterQuestionsCount, questionsCount)
        if (prevSurvey != null) {
            survey.filterQuesions = prevSurvey.filterQuesions
            survey.questions = prevSurvey.questions
        }
        survey.loadedTime = System.currentTimeMillis()

        return survey
    }

    private fun loadQuestion(survey: Survey, category: QuestionCategory, index: Int) : Survey {
        val blovote = getBlovoteContract(survey.address)

        val type : QuestionType
        val title : String
        val points : MutableList<String> = ArrayList()
        val answers : List<Int>
        val newQuestions : MutableList<Question>

        if (category == QuestionCategory.MainQuestion) {
            val infoTuple = blovote.getQuestionInfo(BigInteger.valueOf(index.toLong())).send()
            type = QuestionType.fromContractQuestionType(infoTuple.value1)
            title = infoTuple.value2.toString(Charset.forName("UTF-8"))
            answers = ArrayList()
            newQuestions = ArrayList(survey.questions)

            val pointsCount = blovote.getQuestionPointsCount(index.toBigInteger()).send().toInt()
            for (i in 0 until pointsCount) {
                points.add(blovote.getQuestionPointInfo(BigInteger.valueOf(index.toLong()),
                        i.toBigInteger()).send().toString(Charset.forName("UTF-8")))
            }

        } else {
            val infoTyple = blovote.getFilterQuestionInfo(BigInteger.valueOf(index.toLong())).send()
            type = QuestionType.fromContractQuestionType(infoTyple.value1)
            title = infoTyple.value2.toString(Charset.forName("UTF-8"))
            answers = infoTyple.value3.map { it -> it.toInt() }
            newQuestions = ArrayList(survey.filterQuesions)

            val pointsCount = blovote.getFilterQuestionPointsCount(index.toBigInteger()).send().toInt()
            for (i in 0 until pointsCount) {
                points.add(blovote.getFilterQuestionPointInfo(BigInteger.valueOf(index.toLong()),
                        i.toBigInteger()).send().toString(Charset.forName("UTF-8")))
            }
        }

        while (newQuestions.size <= index) {
            newQuestions.add(Question())
        }
        newQuestions[index] = Question(title, type, points, answers)

        if (category == QuestionCategory.MainQuestion) {
            survey.questions = newQuestions
        } else {
            survey.filterQuesions = newQuestions
        }

        surveysStorage.saveSurvey(survey)
        return survey
    }


    private fun getBlovoteContract(address: String) : Contracts_BlovoteImpl_sol_BlovoteImpl {
        return Contracts_BlovoteImpl_sol_BlovoteImpl.load(address, web3j, accountStorage.getCredentials(),
                gasPrice, Contract.GAS_LIMIT)
    }

}