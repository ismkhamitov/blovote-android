package com.blovote.surveys.ui.monitoring

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.QuestionType
import com.blovote.surveys.data.entities.Respond
import com.blovote.surveys.data.entities.Survey

class AnswersAdapter(private val survey: Survey, private val respond: Respond) : RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_answers_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return respond.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (survey.questions.size <= position || respond.data.size <= position) {
            throw IllegalStateException()
        }

        val titleView = holder.itemView.findViewById<TextView>(R.id.text_view_question_title)
        val textAnswerView = holder.itemView.findViewById<TextView>(R.id.text_view_text_answer)

        val question = survey.questions[position]
        titleView.text = question.title
        if (question.type != QuestionType.Text) {
            textAnswerView.visibility = View.GONE

            val linearLayout = holder.itemView.findViewById<LinearLayout>(R.id.layout_points_list)
            linearLayout.removeAllViews()
            setItems(position, linearLayout)
        } else {
            textAnswerView.text = respond.data[position].data[0]
        }

    }

    private fun setItems(position: Int, layoutPoints: LinearLayout) {
        val question = survey.questions[position]
        val answers = respond.data[position].data.map { it -> it.toInt() }
        for (i in 0 until question.points.size) {
            val pointView = LayoutInflater.from(layoutPoints.context).inflate(
                    if (question.type == QuestionType.SingleVariant) R.layout.layout_point_item_radio
                    else R.layout.layout_point_item_check,
                    layoutPoints, false)

            val selectionView = pointView.findViewById<CompoundButton>(R.id.point_selection_item)
            selectionView.isChecked = answers.contains(i)
            val pointDescriptionView = pointView.findViewById<TextView>(R.id.text_view_point_description)
            pointDescriptionView.text = question.points[i]

            layoutPoints.addView(pointView)
        }
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

}