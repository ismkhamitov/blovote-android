package com.blovote.surveys.data

import android.arch.lifecycle.LiveData
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Respond
import com.blovote.surveys.data.entities.Survey

interface SurveysStorage {

    fun getSurvey(address: String) : Survey?

    fun getLastSurveyIndex(): Int

    fun getAllSurveys(): LiveData<List<Survey>>

    fun getCreatorsSurveys(address: String) : LiveData<List<Survey>>

    fun saveSurvey(survey: Survey)

    fun saveSurveys(surveys: List<Survey>)

    fun updateSurveyQuestions(address: String, questions : List<Question>)


    fun deleteSurvey(survey: Survey)

    fun deleteSurvey(address: String)


    fun saveRespond(surveyAddress: String, index: Int, data: List<List<String>>)

    fun getResponds(surveyAddress: String) : LiveData<List<Respond>>
}