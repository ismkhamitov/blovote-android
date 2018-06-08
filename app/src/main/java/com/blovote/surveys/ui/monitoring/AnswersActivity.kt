package com.blovote.surveys.ui.monitoring

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.blovote.R
import com.blovote.app.App
import com.blovote.app.BlovoteActivity
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_answers_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

class AnswersActivity : BlovoteActivity() {

    @Inject
    lateinit var surveysInteractor: SurveysInteractor

    private lateinit var survey: Survey
    private var currentIndex = -1

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_answers_activity)

        App.appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        disposable.add(surveysInteractor.getSurvey(intent.getStringExtra(KEY_SURVEY_ADDRESS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { survey, throwable ->
                    if (throwable != null) {
                        Toast.makeText(this, "Unable to load survey", Toast.LENGTH_SHORT)
                                .show()
                        finish()
                    } else {
                        this.survey = survey
                        setupUi()
                        setupUx()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        disposable = CompositeDisposable()
    }



    private fun setupUi() {
        currentIndex = 0
        button_prev_respond.isClickable = true
        button_next_respond.isClickable = true
        update()
    }


    private fun setupUx() {
        button_prev_respond.onClick {
            if (currentIndex > 0) {
                --currentIndex
                update()
            }
        }

        button_next_respond.onClick {
            if (currentIndex < survey.currentRespCount - 1) {
                ++currentIndex
                update()
            }
        }
    }


    private fun update() {
        updateCounter()
        updateAnswersFragment()
    }

    private fun updateAnswersFragment() {
        val fragment = AnswersListFragment.newInstance(survey.address, currentIndex)
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_answers_content, fragment)
                .commit()
    }


    private fun updateCounter() {
        text_view_responds_counter.text = String.format("%d/%d", currentIndex + 1, survey.currentRespCount)
    }

    companion object {

        private val KEY_SURVEY_ADDRESS = "survey_address"

        fun getStartIntent(context: Context, surveyAddress: String) : Intent {
            val intent = Intent(context, AnswersActivity::class.java)

            intent.putExtra(KEY_SURVEY_ADDRESS, surveyAddress)
            return intent
        }

    }
}