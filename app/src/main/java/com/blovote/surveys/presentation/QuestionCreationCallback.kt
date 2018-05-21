package com.blovote.surveys.presentation

import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory

interface QuestionCreationCallback {

    fun onRequestQuestionAdd(category: QuestionCategory, question: Question)

    fun onRequestQuestionChange(category: QuestionCategory, question: Question, position: Int)

    fun onRequestQuestionDelete(category: QuestionCategory, index: Int)
}