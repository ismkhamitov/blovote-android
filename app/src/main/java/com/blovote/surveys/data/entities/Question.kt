package com.blovote.surveys.data.entities

import java.io.Serializable

data class Question(var title : String = "",
                    var type: QuestionType = QuestionType.SingleVariant,
                    var points : List<String> = ArrayList(),
                    var answers : List<Int> = ArrayList()) : Serializable