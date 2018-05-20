package com.blovote.surveys.data.entities

enum class QuestionType {

    SingleVariant,
    ManyVariants,
    Text;


    public fun getContractQuestionType() : Int {
        return when(this) {
            Text -> 0
            SingleVariant -> 1
            ManyVariants -> 2
        }
    }

}