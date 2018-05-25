package com.blovote.surveys.data.entities

import com.blovote.R

enum class SurveyState(val nameResId: Int) {

    New(R.string.state_name_new),
    Active(R.string.state_new_active),
    Finished(R.string.state_name_finished);

}