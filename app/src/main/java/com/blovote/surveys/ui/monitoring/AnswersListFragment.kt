package com.blovote.surveys.ui.monitoring

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.blovote.R
import com.blovote.app.App
import com.blovote.surveys.data.entities.Respond
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class AnswersListFragment : Fragment() {

    @Inject
    lateinit var surveysInteractor: SurveysInteractor


    private lateinit var surveyAddress: String
    private var answersIndex: Int = -1


    private var disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_answers_list_fragment, container, false)

        surveyAddress = arguments!!.getString(KEY_SURVEY_ADDRESS)
        answersIndex = arguments!!.getInt(KEY_ANSWERS_INDEX)

        App.appComponent.inject(this)
        setupUi(view)

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    private fun setupUi(view: View) {

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_loading_answers)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_answers_list)

        disposable.add(surveysInteractor.loadRespondInfo(surveyAddress, answersIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // all is ok
                }, {
                    showErrorMessage()
                }))

        disposable.add(surveysInteractor.getSurvey(surveyAddress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadedSurvey, throwable ->
                    if (throwable != null) {
                        showErrorMessage()
                    } else {
                        surveysInteractor.updateSurveyAllQuestionsInfo(loadedSurvey)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { updatedSurvey, throwable ->
                                    if (throwable != null) {
                                        showErrorMessage()
                                    } else {
                                        surveysInteractor.getRespond(this@AnswersListFragment,
                                                surveyAddress, answersIndex)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe { respond ->
                                                    recyclerView.apply {
                                                        layoutManager = LinearLayoutManager(this@AnswersListFragment.context)
                                                        adapter = AnswersAdapter(updatedSurvey, respond)
                                                    }
                                                    progressBar.visibility = View.GONE
                                                    recyclerView.visibility = View.VISIBLE
                                                }
                                    }
                                }
                    }
                })
    }
    private fun showErrorMessage() {
        val context = context
        if (context != null) {
            AlertDialog.Builder(context)
                    .setMessage("Unable to load respond info")
                    .show()
        }
    }

    companion object {


        private val KEY_SURVEY_ADDRESS = "survey"
        private val KEY_ANSWERS_INDEX = "answers_index"

        fun newInstance(surveyAddress: String, answersIndex: Int) : AnswersListFragment {
            val fragment = AnswersListFragment()

            val bundle = Bundle()

            bundle.putString(KEY_SURVEY_ADDRESS, surveyAddress)
            bundle.putInt(KEY_ANSWERS_INDEX, answersIndex)

            fragment.arguments = bundle

            return fragment
        }

    }
}