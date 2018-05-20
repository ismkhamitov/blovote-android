package com.blovote.surveys.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.blovote.R
import com.blovote.app.App
import com.blovote.app.BlovoteActivity
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.presentation.SurveyCreationPresenter
import com.blovote.surveys.ui.questions.EditQuestionFragment
import com.blovote.surveys.ui.questions.QuestionTitleClickListener
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class CreateSurveyActivity : BlovoteActivity(), QuestionTitleClickListener {

    @Inject
    lateinit var surveysInteractor: SurveysInteractor
    lateinit var surveyCreationPresenter: SurveyCreationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        setupUi()
        setupUx()
        setupData()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onQuestionTitleClick(category: QuestionCategory, position: Int) {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, EditQuestionFragment.newInstance(category, position), null)
                .addToBackStack(null)
                .commit()
    }


    private fun setupUi() {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, CreateSurveyFragment(), null)
                .commit()

        supportActionBar?.title = getString(R.string.create_survey)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @UiThread
    private fun setupUx() {
        surveyCreationPresenter = SurveyCreationPresenter(surveysInteractor, ArrayList(), ArrayList())
    }

    private fun setupData() {

    }



    fun onAddQuestionClick() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(getString(R.string.question_type_dialog_title))
                .setItems(this.resources.getStringArray(R.array.question_categories),
                        { dialogInterface, index ->
                            val category = if (index == 0) QuestionCategory.FilterQuestion else QuestionCategory.MainQuestion
                            val fragment = EditQuestionFragment.newInstance(category)

                            launch(UI) {
                                supportFragmentManager.beginTransaction()
                                        .add(android.R.id.content, fragment, null)
                                        .addToBackStack(null)
                                        .commit()
                            }
                        })


        alertDialogBuilder.create().show()
    }

    fun onQuestionAdded() {
        supportFragmentManager.popBackStack()
    }

    fun onQuestionTitleClick() {
        //TODO: implement
    }


    companion object {

        private val TAG_CREATE_SURVEY = "create_survey"

        fun getStartIntent(context: Context) : Intent {
            val intent = Intent(context, CreateSurveyActivity::class.java)
            return intent
        }

    }
}