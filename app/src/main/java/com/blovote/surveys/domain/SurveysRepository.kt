package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Observable

interface SurveysRepository {

    fun getAllSurveys() : List<Survey>

    fun observeAllSurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>>

    fun updateSurveys() : List<Survey>

}