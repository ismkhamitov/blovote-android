package com.blovote.surveys.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.concurrent.TimeUnit

const val COL_ETH_ADDRESS = "eth_address"

@Entity(tableName = "surveys")
data class Survey(@PrimaryKey @ColumnInfo(name = COL_ETH_ADDRESS) var address: String = "",
                  @ColumnInfo(name = "index") var index : Int = 0,
                  @ColumnInfo(name = "creator") var creator : String = "",
                  @ColumnInfo(name = "title") var title: String = "",
                  @ColumnInfo(name = "state") var state: SurveyState = SurveyState.New,
                  @ColumnInfo(name = "creation_time") var timestamp: Long = 0,
                  @ColumnInfo(name = "req_resp_count") var requiresRespondentsCnt: Int = 0,
                  @ColumnInfo(name = "current_resp_count") var currentRespCount: Int = 0,
                  @ColumnInfo(name = "reward_size") var rewardSize: String = "0",
                  @ColumnInfo(name = "filter_questions_count") var filterQuestionsCount : Int = -1,
                  @ColumnInfo(name = "questions_count") var questionsCount : Int = -1,
                  @ColumnInfo(name = "ta_questions") var filterQuesions : List<Question> = ArrayList(),
                  @ColumnInfo(name = "questions") var questions: List<Question> = ArrayList()) {

    var loadedTime: Long = 0

    val fresh : Boolean
        get() { return Math.abs(System.currentTimeMillis() - loadedTime) < TimeUnit.MINUTES.toMillis(1L) }

}