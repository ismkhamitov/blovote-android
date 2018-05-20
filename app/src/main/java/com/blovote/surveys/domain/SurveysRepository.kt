package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Completable
import io.reactivex.Observable
import java.math.BigInteger

interface SurveysRepository {

    fun getAllSurveys() : List<Survey>

    fun observeAllSurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>>

    fun updateSurveys() : List<Survey>

    fun createSurvey(title: String,
                     requiredRespondentsCount: Int,
                     rewardSize: BigInteger,
                     filterQuestions: List<Question> = ArrayList(),
                     mainQuestions: List<Question>) : Completable

}