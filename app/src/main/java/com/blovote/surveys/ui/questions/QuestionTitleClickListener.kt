package com.blovote.surveys.ui.questions

import com.blovote.surveys.data.entities.QuestionCategory

interface QuestionTitleClickListener {

    fun onQuestionTitleClick(category: QuestionCategory, position: Int)

}