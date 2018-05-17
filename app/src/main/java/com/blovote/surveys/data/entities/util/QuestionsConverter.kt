package com.blovote.surveys.data.entities.util

import android.arch.persistence.room.TypeConverter
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.SurveyState
import org.json.JSONArray
import org.json.JSONObject
import java.util.LinkedList

const val TITLE_KEY = "title"
const val POINTS_KEY = "points"

class QuestionsConverter {

    @TypeConverter
    fun toQuestionsString(questions: List<Question>) : String {
        val questionsArray = JSONArray()
        for (question in questions) {
            val pointsArray = JSONArray()
            for (point in question.points) {
                pointsArray.put(point)
            }

            val questionObj = JSONObject()
            questionObj.put(TITLE_KEY, question.title)
            questionObj.put(POINTS_KEY, pointsArray)

            questionsArray.put(questionObj)
        }

        return questionsArray.toString()
    }

    @TypeConverter
    fun fromQuestionsString(questionsJson : String) : List<Question> {
        val questions = LinkedList<Question>()

        val questionsArray = JSONArray(questionsJson)
        for (i in 0 until questionsArray.length()) {
            val questionJson = questionsArray.getJSONObject(i)
            val pointsJson = questionJson.getJSONArray(POINTS_KEY)

            val points = LinkedList<String>()
            for (j in 0 until pointsJson.length()) {
                points.add(pointsJson.getString(j))
            }

            questions.add(Question(questionJson.getString(TITLE_KEY), points))
        }

        return questions
    }

    @TypeConverter
    fun stateToInt(state : SurveyState) : Int {
        return state.ordinal
    }

    @TypeConverter
    fun stateFromInt(int : Int) : SurveyState {
        return SurveyState.values()[int]
    }

}