package com.blovote.surveys.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.blovote.surveys.data.entities.Respond
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.data.entities.util.QuestionsConverter
import com.blovote.surveys.data.entities.util.RespondsConverter


@Database(entities = [ Survey::class, Respond::class ], version = 1)
@TypeConverters(QuestionsConverter::class, RespondsConverter::class)
abstract class SurveysDatabase : RoomDatabase() {

    abstract fun surveysDao() : SurveysDao

    companion object {

        private val instances: MutableMap<String, SurveysDatabase> = mutableMapOf()

        fun getInstance(context: Context, name : String) : SurveysDatabase {
            if (!instances.containsKey(name)) {
                synchronized(SurveysDatabase::class) {
                    if (!instances.containsKey(name)) {
                        instances[name] = Room.databaseBuilder(context, SurveysDatabase::class.java, name).build()
                    }
                }
            }

            return instances[name]!!
        }

    }


}