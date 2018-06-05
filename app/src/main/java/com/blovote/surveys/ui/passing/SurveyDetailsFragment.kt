package com.blovote.surveys.ui.passing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.blovote.R
import com.blovote.app.App
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.ui.toReadableString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.math.BigInteger
import javax.inject.Inject

class SurveyDetailsFragment : Fragment() {

    private lateinit var titleView : TextView
    private lateinit var rewardTextView : TextView
    private lateinit var progressBarQuestions : ProgressBar
    private lateinit var progressBarState : ProgressBar
    private lateinit var stateTextView : TextView
    private lateinit var questionsCountTextView: TextView
    private lateinit var progressBarCurrResp : ProgressBar
    private lateinit var textViewCurrResp : TextView
    private lateinit var buttonViewResults : Button
    private lateinit var buttonExportResults : Button
    private lateinit var buttonStart : Button

    private lateinit var address : String
    private var index : Int = -1
    private lateinit var survey: Survey
    private var monitorMode : Boolean = false


    @Inject
    lateinit var surveysInteractor : SurveysInteractor

    var disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_survey_details, container, false)
        App.appComponent.inject(this)
        setupUI(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupUX()
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    private fun setupUI(view: View) {
        address = arguments!!.getString(KEY_ADDRESS)
        index = arguments!!.getInt(KEY_INDEX)
        monitorMode = arguments!!.getBoolean(KEY_MONITOR_MODE)

        titleView = view.findViewById(R.id.title)
        rewardTextView = view.findViewById(R.id.text_view_reward_unit)
        progressBarQuestions = view.findViewById(R.id.progress_bar_questions_count)
        progressBarState = view.findViewById(R.id.progress_bar_survey_state)
        stateTextView = view.findViewById(R.id.text_view_survey_state)
        questionsCountTextView = view.findViewById(R.id.text_view_questions_count)
        progressBarCurrResp = view.findViewById(R.id.progress_bar_curr_resp_count)
        textViewCurrResp = view.findViewById(R.id.text_view_curr_resp_count)
        buttonViewResults = view.findViewById(R.id.button_view_results)
        buttonExportResults = view.findViewById(R.id.button_export_results)
        buttonStart = view.findViewById(R.id.button_create)

        val delimiterCurrRespCount = view.findViewById<View>(R.id.delimiter_curr_resp_count)
        val layoutCurrRespCount = view.findViewById<LinearLayout>(R.id.layout_curr_resp_count)
        if (!monitorMode) {
            delimiterCurrRespCount.visibility = View.GONE
            layoutCurrRespCount.visibility = View.GONE
            buttonViewResults.visibility = View.GONE
            buttonExportResults.visibility = View.GONE
        } else {
            buttonStart.visibility = View.GONE
        }

    }

    private fun setupUX() {
        disposable.add(surveysInteractor.getSurvey(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadedSurvey, _ ->
                    survey = loadedSurvey

                    titleView.text = survey.title
                    rewardTextView.text = BigInteger(survey.rewardSize).toReadableString()

                    if (survey.fresh) {
                        onSurveyLoaded(survey)
                    } else {
                        surveysInteractor.updateSurveyInfo(survey)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ newSurvey ->
                                    onSurveyLoaded(newSurvey)
                                })
                    }
                })
    }

    private fun onSurveyLoaded(survey: Survey?) {
        if (survey == null || context == null) {
            return
        }

        progressBarState.visibility = View.GONE
        progressBarQuestions.visibility = View.GONE
        progressBarCurrResp.visibility = View.GONE

        stateTextView.text = getString(survey.state.nameResId)
        stateTextView.visibility = View.VISIBLE

        questionsCountTextView.text = (survey.filterQuestionsCount + survey.questionsCount).toString()
        questionsCountTextView.visibility = View.VISIBLE

        textViewCurrResp.text = survey.currentRespCount.toString()
        textViewCurrResp.visibility = View.VISIBLE

        if (survey.currentRespCount > 0) {
            buttonViewResults.isEnabled = true
            buttonExportResults.isEnabled = true
            //TODO: handle clicks
        }
        buttonStart.isEnabled = true
        buttonStart.onClick {
            (activity as? QuestionPassListener)?.onPassNext(survey)
        }
    }


    companion object {

        private val KEY_ADDRESS = "address"
        private val KEY_INDEX = "index"
        private val KEY_MONITOR_MODE = "monitor"

        fun newInstance(surveyAddress: String, index: Int, monitorState: Boolean) : Fragment {
            val fragment = SurveyDetailsFragment()

            val bundle = Bundle()
            bundle.putString(KEY_ADDRESS, surveyAddress)
            bundle.putInt(KEY_INDEX, index)
            bundle.putBoolean(KEY_MONITOR_MODE, monitorState)

            fragment.arguments = bundle
            return fragment
        }

    }

}