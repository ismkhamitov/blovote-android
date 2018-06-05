package com.blovote.surveys.ui.monitoring

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.blovote.R
import com.blovote.app.App
import com.blovote.app.BlovoteActivity
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.ui.passing.SurveyDetailsFragment
import javax.inject.Inject

class MySurveyActivity : BlovoteActivity() {

    private lateinit var address : String
    private var index : Int = -1

    @Inject
    lateinit var surveysInteractor: SurveysInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.survey)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        App.appComponent.inject(this)

        address = intent.extras.getString(KEY_ADDRESS)
        index = intent.extras.getInt(KEY_INDEX)

        updateQuestionFragment(SurveyDetailsFragment.newInstance(address, index, true),true)
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
            val intent = Intent(context, MySurveyActivity::class.java)
            intent.putExtra(KEY_ADDRESS, surveyAddress)
            intent.putExtra(KEY_INDEX, index)

            return intent
        }

    }

}