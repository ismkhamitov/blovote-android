package com.blovote.surveys.ui.passing

import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.Survey

interface QuestionPassListener {

    fun onPassNext(passingSurvey: Survey)

    fun onQuestionAnswer(questionCategory: QuestionCategory, questionIndex: Int, answers: List<String>);

}