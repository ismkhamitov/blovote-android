package com.blovote.surveys.data

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.util.Base64
import com.blovote.account.data.AccountStorage
import com.blovote.contracts.ContractBloGodImpl
import com.blovote.contracts.ContractBlovoteImpl
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.data.entities.SurveyState
import com.blovote.surveys.domain.SurveysRepository
import io.reactivex.Observable
import org.spongycastle.util.Strings
import org.web3j.protocol.Web3j
import org.web3j.tx.Contract
import java.math.BigInteger
import java.nio.charset.Charset

class SurveysRepositoryImpl(private val surveysStorage: SurveysStorage,
                            private val web3j : Web3j,
                            private val godContractAddress : String,
                            private val accountStorage: AccountStorage) : SurveysRepository {

    private val gasPrice by lazy { web3j.ethGasPrice().send().gasPrice.multiply(BigInteger.valueOf(5L)) }
    private val contractBloGodImpl by lazy { ContractBloGodImpl.load(godContractAddress, web3j, accountStorage.loadCredentials(),
            gasPrice, Contract.GAS_LIMIT) }

    override fun getAllSurveys(): List<Survey> {
        return surveysStorage.getAllSurveys().value ?: listOf()
    }

    override fun observeAllSurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>> {
        return Observable.create<List<Survey>> {it ->
            surveysStorage.getAllSurveys().observe(lifecycleOwner,
                    Observer<List<Survey>> { t -> it.onNext(t!!) })
        }
    }

    override fun updateSurveys(): List<Survey> {
//        val creds = accountStorage.loadCredentials()
        val surveysNumder = contractBloGodImpl.surveysNumber.send()
        val lastIndex = surveysStorage.getLastSurveyIndex()

        val newAddresses = contractBloGodImpl.getBlovoteAddresses(BigInteger.valueOf(lastIndex + 1L),
                surveysNumder).send()

        val list = mutableListOf<Survey>()
        for (i in newAddresses.indices) {
            list.add(loadBlovote(newAddresses[i].toString(), lastIndex + 1 + i))
        }

        surveysStorage.saveSurveys(list)

        return list
    }


    override fun createSurvey(title: String,
                        requiredRespondentsCount: Int,
                        rewardSize: BigInteger,
                        filterQuestions : List<Question>,
                        mainQuestions: List<Question>) {

        val prevSurveysNumber = contractBloGodImpl.surveysNumber.send()
//        Base64.encode(Base64.NO_WRAP)
        contractBloGodImpl.createNewSurvey(title.toByteArray(Charset.forName("UTF-8")),
                BigInteger.valueOf(requiredRespondentsCount.toLong()),
                rewardSize).send()
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

    private fun loadBlovote(address : String, index : Int) : Survey {
        val blovote = getBlovoteContract(address)
        val title = blovote.title().send().toString(Charset.forName("UTF-8"))
        val state = SurveyState.values()[blovote.state.send().toInt()]
        val creationTime = blovote.creationTimestamp().send().toLong()
        val requiredRespCnt = blovote.requiredRespondentsCount().send().toInt()
        val currentRespCnt = blovote.currentRespondentsCount().send().toInt()
        val rewardSize  = blovote.rewardSize().send().toString()
        val questionsCount = blovote.questionsCount.send().toInt()

        return Survey(address, index, title, state, creationTime, requiredRespCnt, currentRespCnt, rewardSize, questionsCount)
    }


    private fun getBlovoteContract(address: String) : ContractBlovoteImpl {
        return ContractBlovoteImpl.load(address, web3j, accountStorage.loadCredentials(),
                gasPrice, Contract.GAS_LIMIT)
    }


    companion object {
        private val REWARD_DIV_EXP = 12
    }

}