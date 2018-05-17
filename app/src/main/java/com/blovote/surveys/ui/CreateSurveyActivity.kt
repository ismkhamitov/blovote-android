package com.blovote.surveys.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.blovote.R
import com.blovote.app.BlovoteActivity

class CreateSurveyActivity : BlovoteActivity()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_create_survey_container)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun getStartIntent(context: Context) : Intent {
            val intent = Intent(context, CreateSurveyActivity::class.java)
            return intent
        }

    }
}