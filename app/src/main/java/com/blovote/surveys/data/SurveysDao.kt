package com.blovote.surveys.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey


@Dao
interface SurveysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSurveys(surveys : List<Survey>)

    @Delete
    fun deleteSurvey(survey: Survey)

    @Query("DELETE FROM surveys WHERE eth_address = :address")
    fun deleteSurvey(address: String)

    @Query("SELECT * FROM surveys ORDER BY `index` DESC LIMIT 1")
    fun getLastSurvey() : List<Survey>

    @Query("SELECT * FROM surveys ORDER BY creation_time DESC")
    fun getAllSurveys() : LiveData<List<Survey>>

    @Query("SELECT * FROM surveys WHERE creator = :address ORDER BY creation_time DESC")
    fun getCreatorsSurveys(address: String): LiveData<List<Survey>>

    @Query("SELECT * FROM surveys WHERE creation_time >= :timestamp ORDER BY creation_time DESC")
    fun getSurveysAfter(timestamp : Long) : LiveData<List<Survey>>

    @Query("UPDATE surveys SET questions = :questions WHERE eth_address = :address")
    fun updateQuestions(address : String, questions : List<Question>)

    @Query("SELECT * FROM surveys WHERE eth_address = :address")
    fun getSurveyByAddress(address: String): List<Survey>

}