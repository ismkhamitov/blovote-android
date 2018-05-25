package com.blovote.surveys.data.entities

import java.math.BigInteger

enum class QuestionType {

    SingleVariant,
    ManyVariants,
    Text;


    fun getContractQuestionType() : Int {
        return when(this) {
            Text -> 0
            SingleVariant -> 1
            ManyVariants -> 2
        }
    }

    companion object {

        fun fromContractQuestionType(int: BigInteger) : QuestionType {
            return when(int) {
                BigInteger.ZERO -> Text
                BigInteger.ONE -> SingleVariant
                BigInteger.valueOf(2L) -> ManyVariants
                else -> throw IllegalArgumentException()
            }
        }

    }

}