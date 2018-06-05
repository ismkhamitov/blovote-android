package com.blovote.surveys.ui.passing

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.blovote.R
import com.blovote.app.App
import com.blovote.app.BlovoteActivity
import com.blovote.app.CommonProgressFragment
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.data.entities.SurveyState
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.domain.SurveysRepository
import com.blovote.surveys.ui.creation.CreateSurveyActivity
import com.blovote.surveys.ui.toReadableString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_survey_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.web3j.abi.datatypes.Bool
import java.math.BigInteger
import javax.inject.Inject

class SurveyActivity : BlovoteActivity(), QuestionPassListener {

    private lateinit var address : String
    private var index : Int = -1

    private var disposable = CompositeDisposable()

    @Inject
    lateinit var surveysInteractor: SurveysInteractor

    private lateinit var survey : Survey

    private var answeredFilterQuestions : Int = 0
    private var anweredMainQuestions : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.survey)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        App.appComponent.inject(this)

        address = intent.extras.getString(KEY_ADDRESS)
        index = intent.extras.getInt(KEY_INDEX)

        updateQuestionFragment(SurveyDetailsFragment.newInstance(address, index, false),true)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (isFinishable()) {
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (isFinishable()) {
            super.onBackPressed()
        }
    }

    private fun isFinishable() : Boolean {
        return supportFragmentManager.findFragmentByTag(TAG_QUESTION_FRAGMENT) == null
                && !supportFragmentManager.popBackStackImmediate();
    }

    override fun onPassNext(passingSurvey: Survey) {
        survey = passingSurvey
        val msg = if (survey.state != SurveyState.Active) {
            getString(R.string.msg_survey_not_active)
        } else if (!hasNotAnsweredQuestions()) {
            getString(R.string.question_answered)
        } else null

        if (msg != null) {
            showErrorMessage(msg)
        } else {
            val nextQuestionInfo = getNextQuestionInfo()
            onStartAnsweringQuestion(nextQuestionInfo.first, nextQuestionInfo.second)
        }
    }


    override fun onQuestionAnswer(questionCategory: QuestionCategory, questionIndex: Int, answers: List<String>) {
        updateQuestionFragment(CommonProgressFragment.newInstance(getString(R.string.msg_uploading_answer)),false)

        if (questionCategory == QuestionCategory.FilterQuestion) {
            disposable.add(surveysInteractor.checkAnswer(survey, questionIndex, answers)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, throwable ->
                        if (!result) {
                            showErrorMessage(getString(R.string.msg_incorrect_answer))
                        } else {
                            //TODO: save passing fact to some storage
                            ++answeredFilterQuestions
                            onPassNext(survey)
                        }
                    })
        } else {
            disposable.add(surveysInteractor.uploadAnswer(survey, questionIndex, answers)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        //TODO: save passing fact to some storage
                        ++anweredMainQuestions
                        onPassNext(survey)
                    }, {
                        showErrorMessage(getString(R.string.msg_error_uploading, it.message))
                    })
            )
        }
    }

    private fun hasNotAnsweredQuestions() : Boolean {
        return survey.filterQuestionsCount > answeredFilterQuestions ||
                survey.questionsCount > anweredMainQuestions

    }

    private fun getNextQuestionInfo() : Pair<QuestionCategory, Int> {
        if (survey.filterQuestionsCount > answeredFilterQuestions) {
            return Pair(QuestionCategory.FilterQuestion, answeredFilterQuestions)
        } else if (survey.questionsCount > anweredMainQuestions) {
            return Pair(QuestionCategory.MainQuestion, anweredMainQuestions)
        }

        throw IllegalStateException()
    }


    private fun onStartAnsweringQuestion(category: QuestionCategory, index: Int) {
        val progressFragment = CommonProgressFragment.newInstance(getString(R.string.msg_loading_question_details))
        updateQuestionFragment(progressFragment, false)

        val needToLoadDetails = if (category == QuestionCategory.MainQuestion)
            survey.questions.size <= index || survey.questions[index].title.isEmpty()
        else
            survey.filterQuesions.size <= index || survey.filterQuesions[index].title.isEmpty()

        if (needToLoadDetails) {
            disposable.add(surveysInteractor.updateSurveyQuestionInfo(survey, category, index)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { survey, throwable ->
                        showQuestion(category, index)
                    }
            )
        } else {
            showQuestion(category, index)
        }

    }


    private fun showQuestion(category: QuestionCategory, index: Int) {
        val question = if (category == QuestionCategory.MainQuestion)
            survey.questions[index]
        else
            survey.filterQuesions[index]

        updateQuestionFragment(QuestionPassFragment.newInstance(category, index, question), false)
    }

    private fun showErrorMessage(msg: String) {
        AlertDialog.Builder(this)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                    finish()
                }
                .show()
    }

    private fun updateQuestionFragment(fragment: Fragment, closeable: Boolean) {
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment, if (closeable) null else TAG_QUESTION_FRAGMENT)
                .commit()
    }


    companion object {

        private val TAG_QUESTION_FRAGMENT = "huyo-moyo-tag"

        private val KEY_ADDRESS = "address"
        private val KEY_INDEX = "index"

        fun getStartIntent(context: Context, surveyAddress: String, index: Int) : Intent {
            val intent = Intent(context, SurveyActivity::class.java)
            intent.putExtra(KEY_ADDRESS, surveyAddress)
            intent.putExtra(KEY_INDEX, index)

            return intent
        }

    }

}