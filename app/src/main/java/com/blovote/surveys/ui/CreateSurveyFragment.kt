package com.blovote.surveys.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.blovote.R
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.presentation.SurveyCreationPresenter
import org.jetbrains.anko.sdk25.coroutines.onClick

class CreateSurveyFragment : Fragment() {

    lateinit var surveyCreationPresenter : SurveyCreationPresenter

    lateinit var recyclerFilterQuestions : RecyclerView
    lateinit var recyclerMainQuestions : RecyclerView
    lateinit var adapterFilterQuetions : SurveyCreationAdapter
    lateinit var adapterMainQuetions : SurveyCreationAdapter

    lateinit var fabAddQuestion : FloatingActionButton
    lateinit var buttonAddQuestion : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_create_survey_container, container, false)

        setupUI(view)
        setupUX()

        return view
    }


    private fun setupUI(view: View) {
        val parentActivity = activity as CreateSurveyActivity

        fabAddQuestion = view.findViewById(R.id.fab_add_question)
        fabAddQuestion.onClick { parentActivity.onAddQuestionClick() }

        buttonAddQuestion = view.findViewById(R.id.button_create)

        surveyCreationPresenter = parentActivity.surveyCreationPresenter

        val linearLayoutManagerFilter = LinearLayoutManager(context)
        val linearLayoutManagerMain = LinearLayoutManager(context)

        adapterFilterQuetions = SurveyCreationAdapter(surveyCreationPresenter.observeFilterQuestions(),
                QuestionCategory.FilterQuestion, parentActivity, surveyCreationPresenter)
        recyclerFilterQuestions = view.findViewById(R.id.recycler_view_filter_questions)
        recyclerFilterQuestions.apply {
            layoutManager = linearLayoutManagerFilter
            adapter = adapterFilterQuetions
        }

        adapterMainQuetions = SurveyCreationAdapter(surveyCreationPresenter.observeMainQuestions(),
                QuestionCategory.MainQuestion, parentActivity, surveyCreationPresenter)
        recyclerMainQuestions = view.findViewById(R.id.recycler_view_main_questions)
        recyclerMainQuestions.apply {
            layoutManager = linearLayoutManagerMain
            adapter = adapterMainQuetions
        }
    }


    private fun setupUX() {

    }



}
