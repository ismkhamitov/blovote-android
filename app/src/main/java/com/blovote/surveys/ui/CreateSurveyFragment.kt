package com.blovote.surveys.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.presentation.SurveyCreationPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.math.BigInteger

class CreateSurveyFragment : Fragment() {

    lateinit var surveyCreationPresenter : SurveyCreationPresenter

    lateinit var titleView : TextView
    lateinit var requiredRespCntView : TextView
    lateinit var rewardSizeView : TextView
    lateinit var rewardSizeSpinner : Spinner

    lateinit var hintFilterQuestions : View
    lateinit var hintMainQuestions : View

    lateinit var recyclerFilterQuestions : RecyclerView
    lateinit var recyclerMainQuestions : RecyclerView
    lateinit var adapterFilterQuetions : SurveyCreationAdapter
    lateinit var adapterMainQuetions : SurveyCreationAdapter

    lateinit var fabAddQuestion : FloatingActionButton
    lateinit var buttonAddQuestion : Button

    var disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_create_survey_container, container, false)

        setupUI(view)
        setupUX()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    private fun setupUI(view: View) {
        val parentActivity = activity as CreateSurveyActivity

        titleView = view.findViewById(R.id.title)
        requiredRespCntView = view.findViewById(R.id.edit_text_respondents_count)
        rewardSizeView = view.findViewById(R.id.edit_text_reward_size)
        rewardSizeSpinner = view.findViewById(R.id.spinner_reward_unit)

        fabAddQuestion = view.findViewById(R.id.fab_add_question)
        fabAddQuestion.onClick { parentActivity.onAddQuestionClick() }

        buttonAddQuestion = view.findViewById(R.id.button_create)
        buttonAddQuestion.onClick {
            onCreateSurvey()
        }

        surveyCreationPresenter = parentActivity.surveyCreationPresenter

        hintFilterQuestions = view.findViewById(R.id.target_audience_no_questions_text_view)
        hintMainQuestions = view.findViewById(R.id.main_no_questions_text_view)

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
        disposable.add(surveyCreationPresenter.observeFilterQuestions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                        hintFilterQuestions.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                        recyclerFilterQuestions.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
                }))

        disposable.add(surveyCreationPresenter.observeMainQuestions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                        hintMainQuestions.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                        recyclerMainQuestions.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
                }))
    }


    private fun onCreateSurvey() {
        var isComplete = true
        var notCompleteMsg = ""

        val title = titleView.text.toString()
        if (title.isEmpty()) {
            isComplete = false
            notCompleteMsg = getString(R.string.msg_title_empty)
        }

        val requiredRespCnt = requiredRespCntView.text.toString()
        if (requiredRespCnt.isEmpty() || requiredRespCnt.toInt() < 1) {
            isComplete = false
            notCompleteMsg = getString(R.string.msg_not_complete_respondents)
        }

        val rewardSize = rewardSizeView.text.toString()
        if (rewardSize.isEmpty() || rewardSize.toInt() < 1) {
            isComplete = false
            notCompleteMsg = getString(R.string.msg_not_complete_reward_size)
        }

        val exp = rewardSizeSpinner.selectedItemPosition * 3

        if (isComplete) {
            val rewardValue = BigInteger.valueOf(rewardSize.toLong()).multiply(BigInteger.TEN.pow(exp))
            surveyCreationPresenter.requestQuestionCreation(title, requiredRespCnt.toInt(), rewardValue).subscribe()
        } else if (context != null) {
            AlertDialog.Builder(context!!)
                    .setTitle(getString(R.string.msg_survey_creation_not_complete))
                    .setMessage(notCompleteMsg)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }
                    .create()
                    .show()
        }
    }

}
