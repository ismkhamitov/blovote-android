package com.blovote.surveys.data.entities.util

import android.arch.persistence.room.TypeConverter
import com.blovote.BuildConfig
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionType
import com.blovote.surveys.data.entities.SurveyState
import org.json.JSONArray
import org.json.JSONObject
import java.util.LinkedList

const val TITLE_KEY = "title"
const val TYPE_KEY = "type"
const val POINTS_KEY = "points"
const val ANSWERS_KEY = "answers"

class QuestionsConverter {

    @TypeConverter
    fun toQuestionsString(questions: List<Question>) : String {
        val questionsArray = JSONArray()
        for (question in questions) {
            val pointsArray = JSONArray()
            question.points.forEach { pointsArray.put(it) }
            val answersArray = JSONArray()
            question.answers.forEach { answersArray.put(it) }

            val questionObj = JSONObject()
            questionObj.put(TITLE_KEY, question.title)
            questionObj.put(TYPE_KEY, question.type.ordinal)
            questionObj.put(POINTS_KEY, pointsArray)
            questionObj.put(ANSWERS_KEY, answersArray)

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
            val answersJson = questionJson.getJSONArray(ANSWERS_KEY)

            val points = LinkedList<String>()
            for (j in 0 until pointsJson.length()) {
                points.add(pointsJson.getString(j))
            }

            val answers = LinkedList<Int>()
            for (j in 0 until answersJson.length()) {
                answers.add(answersJson.getInt(i))
            }

            val title = questionJson.getString(TITLE_KEY)

            val typeIndex = questionJson.getInt(TYPE_KEY)
            if (BuildConfig.DEBUG) {
                assert(typeIndex >= 0 && typeIndex < QuestionType.values().size)
            }
            val type = QuestionType.values()[typeIndex]

            questions.add(Question(title, type, points, answers))
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

    @TypeConverter
    fun questionTypeToInt(questionType: QuestionType) : Int {
        return questionType.ordinal
    }

    @TypeConverter
    fun intToQuestionType(int: Int) : QuestionType {
        return QuestionType.values()[int]
    }

}