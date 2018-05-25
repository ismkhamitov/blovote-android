package com.blovote.surveys.data

import android.arch.lifecycle.LiveData
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey

class SurveysStorageImpl(storage: SurveysDatabase) : SurveysStorage {

    private val surveysDao : SurveysDao = storage.surveysDao()


    override fun getSurvey(address: String): Survey? {
        val surveys = surveysDao.getSurveyByAddress(address)
        return if (surveys.isEmpty()) {
            null
        } else {
            surveys[0]
        }
    }

    override fun getLastSurveyIndex(): Int {
        val list = surveysDao.getLastSurvey()
        return if (list.isEmpty()) -1 else list[0].index
    }

    override fun getAllSurveys() : LiveData<List<Survey>> {
        return surveysDao.getAllSurveys()
    }



    override fun saveSurvey(survey: Survey) {
        surveysDao.insertSurveys(listOf(survey))
    }

    override fun saveSurveys(surveys: List<Survey>) {
        surveysDao.insertSurveys(surveys)
    }

    override fun updateSurveyQuestions(address: String, questions: List<Question>) {
        surveysDao.updateQuestions(address, questions)
    }



    override fun deleteSurvey(survey: Survey) {
        surveysDao.deleteSurvey(survey)
    }

    override fun deleteSurvey(address: String) {
        surveysDao.deleteSurvey(address)
    }


}