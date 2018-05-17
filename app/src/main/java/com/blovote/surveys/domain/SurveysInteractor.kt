package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Observable

interface SurveysInteractor {

    fun updateSurveys() : List<Survey>

    fun getExistingSurveys() : List<Survey>

    fun observeExistingSurveys(lifecycleOwner: LifecycleOwner): Observable<List<Survey>>
}