package com.blovote.surveys.presentation

import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.math.BigInteger

class SurveyCreationPresenter(
        val surveysInteractor: SurveysInteractor,
        filterQuestions: List<Question>,
        mainQuestions: List<Question> ) : QuestionCreationCallback {


    private val filterQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(filterQuestions)
    private val mainQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(mainQuestions)

    override fun onRequestQuestionAdd(category: QuestionCategory, question: Question) {
        val subject = if (category == QuestionCategory.FilterQuestion)
            filterQuestionsSubject else mainQuestionsSubject

        val newQuestions = ArrayList<Question>(subject.value)
        newQuestions.add(question)
        subject.onNext(newQuestions)

    }

    override fun onRequestQuestionDelete(category: QuestionCategory, index: Int) {
        val subject = if (category == QuestionCategory.FilterQuestion)
            filterQuestionsSubject else mainQuestionsSubject

        val newQuestions = ArrayList<Question>(subject.value)
        newQuestions.removeAt(index)
        subject.onNext(newQuestions)
    }


    fun requestQuestionCreation(title: String,
                                         requiredRespondentsCount: Int,
                                         rewardSize: BigInteger) : Completable {

        return surveysInteractor.createSurvey(title, requiredRespondentsCount, rewardSize,
                filterQuestions(), mainQuestions())
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