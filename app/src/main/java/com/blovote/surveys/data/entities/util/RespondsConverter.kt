package com.blovote.surveys.data.entities.util

import android.arch.persistence.room.TypeConverter
import com.blovote.surveys.data.entities.Answers
import org.json.JSONArray

class RespondsConverter {


    @TypeConverter
    fun dataToString(answers: List<Answers>) : String {
        val array = JSONArray()
        answers.forEach {
            val answer = JSONArray()
            it.data.forEach { r -> answer.put(r) }
            array.put(answer)
        }

        return array.toString()
    }

    @TypeConverter
    fun stringToData(jsonData: String) : List<Answers> {
        val jsonArray = JSONArray(jsonData)

        val data : MutableList<Answers> = ArrayList()

        for (i in 0 until jsonArray.length()) {
            val answerArray = JSONArray(jsonArray.get(i).toString())
            val answers : MutableList<String> = ArrayList()
            for (j in 0 until answerArray.length()) {
                answers.add(answerArray.get(j).toString())
            }

            data.add(Answers(answers))
        }

        return data
    }

}