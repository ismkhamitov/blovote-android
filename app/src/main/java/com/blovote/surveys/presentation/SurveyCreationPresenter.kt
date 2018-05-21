package com.blovote.surveys.presentation

import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.math.BigInteger

class SurveyCreationPresenter(
        val surveysInteractor: SurveysInteractor,
        filterQuestions: List<Question>,
        mainQuestions: List<Question> ) : QuestionCreationCallback {

    private val filterQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(filterQuestions)
    private val mainQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(mainQuestions)

    private val creationSubject : BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    override fun onRequestQuestionAdd(category: QuestionCategory, question: Question) {
        val subject = if (category == QuestionCategory.FilterQuestion)
            filterQuestionsSubject else mainQuestionsSubject

        val newQuestions = ArrayList<Question>(subject.value)
        newQuestions.add(question)
        subject.onNext(newQuestions)

    }

    override fun onRequestQuestionChange(category: QuestionCategory, question: Question, position: Int) {
        val subject = if (category == QuestionCategory.FilterQuestion) filterQuestionsSubject
            else mainQuestionsSubject

        val newQuestions = ArrayList<Question>(subject.value)

        if (position < 0 || position >= newQuestions.size) {
            throw IndexOutOfBoundsException()
        }

        newQuestions[position] = question
        subject.onNext(newQuestions)

    }

    override fun onRequestQuestionDelete(category: QuestionCategory, index: Int) {
        val subject = if (category == QuestionCategory.FilterQuestion)
            filterQuestionsSubject else mainQuestionsSubject

        val newQuestions = ArrayList<Question>(subject.value)
        newQuestions.removeAt(index)
        subject.onNext(newQuestions)
    }


    fun creationIsInProgressStream() : Observable<Boolean> {
        return creationSubject
    }


    fun requestQuestionCreation(title: String,
                                         requiredRespondentsCount: Int,
                                         rewardSize: BigInteger) : Completable {
        return Completable.create({
            creationSubject.onNext(true)
            surveysInteractor.createSurvey(title, requiredRespondentsCount, rewardSize,
                    filterQuestions(), mainQuestions()).subscribe(object : CompletableObserver {
                override fun onComplete() {
                    creationSubject.onNext(false)
                    surveysInteractor.requestSurveysUpdate()
                    it.onComplete()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    it.onError(e)
                }
            })
        })
    }



    fun filterQuestions() : List<Question> {
        return filterQuestionsSubject.value
    }

    fun mainQuestions() : List<Question> {
        return mainQuestionsSubject.value
    }

    fun observeFilterQuestions() : Observable<List<Question>> {
        return filterQuestionsSubject.observeOn(Schedulers.computation())
    }

    fun observeMainQuestions() : Observable<List<Question>> {
        return mainQuestionsSubject.observeOn(Schedulers.computation())
    }

}