package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigInteger

interface SurveysInteractor {

    fun createSurvey(title: String, requiredRespondentsCnt: Int,
                     rewardSize: BigInteger,
                     filterQuestions: List<Question>,
                     mainQuestions: List<Question>) : Completable

    fun getSurvey(address: String) : Single<Survey>

    fun requestSurveysUpdate() : Completable

    fun getExistingSurveys() : List<Survey>

    fun observeExistingSurveys(lifecycleOwner: LifecycleOwner): Observable<List<Survey>>

    fun observeMySurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>>

    fun updateSurveyInfo(survey: Survey): Single<Survey>

    fun updateSurveyQuestionInfo(survey: Survey, category: QuestionCategory, index: Int) : Single<Survey>

    fun checkAnswer(survey: Survey, questionIndex: Int, answers: List<String>) : Single<Boolean>

    fun uploadAnswer(survey: Survey, questionIndex: Int, answers: List<String>): Completable
}