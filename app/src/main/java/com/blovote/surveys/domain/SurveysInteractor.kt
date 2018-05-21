package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Completable
import io.reactivex.Observable
import java.math.BigInteger

interface SurveysInteractor {

    fun createSurvey(title: String, requiredRespondentsCnt: Int,
                     rewardSize: BigInteger,
                     filterQuestions: List<Question>,
                     mainQuestions: List<Question>) : Completable

    fun requestSurveysUpdate() : Completable

    fun getExistingSurveys() : List<Survey>

    fun observeExistingSurveys(lifecycleOwner: LifecycleOwner): Observable<List<Survey>>
}