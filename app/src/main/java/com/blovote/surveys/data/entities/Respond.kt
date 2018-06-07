package com.blovote.surveys.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "responds", foreignKeys = [
    ForeignKey(entity = Survey::class,
            parentColumns = [COL_ETH_ADDRESS],
            childColumns = ["survey_eth_address"],
            onDelete = ForeignKey.CASCADE)])
data class Respond(@ColumnInfo(name = "survey_eth_address") var address : String,
                   @ColumnInfo(name = "index") var index : Int = 0,
                   @ColumnInfo(name = "data") var data : List<Answers> = ArrayList()) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null

}