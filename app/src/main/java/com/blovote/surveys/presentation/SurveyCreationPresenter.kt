package com.blovote.surveys.presentation

import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SurveyCreationPresenter(filterQuestions: List<Question>, mainQuestions: List<Question> ) : QuestionCreationCallback {


    private val filterQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(filterQuestions)
    private val mainQuestionsSubject: BehaviorSubject<List<Question>> = BehaviorSubject.createDefault(mainQuestions)

    override fun onRequestQuestionAdd(category: QuestionCategory, question: Question) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestQuestionDelete(category: QuestionCategory, index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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