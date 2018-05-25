package com.blovote.surveys.ui.passing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.QuestionType
import com.blovote.surveys.ui.questions.QuestionAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick


class QuestionPassFragment : Fragment() {

    private lateinit var category: QuestionCategory
    private var index = -1
    private lateinit var question : Question
    private lateinit var questionsAdapter : QuestionAdapter

    private lateinit var titleView : TextView
    private lateinit var recyclerQuestions : RecyclerView
    private lateinit var buttonNext : Button
    private lateinit var textAnswerView : EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_question_details, container, false)

        index = arguments!!.getInt(KEY_INDEX)
        category = arguments!!.getSerializable(KEY_CATEGORY) as QuestionCategory
        question = arguments!!.getSerializable(KEY_QUESTION) as Question

        setupUI(view)
        return view
    }

    fun setupUI(view: View) {
        titleView = view.findViewById(R.id.title)
        titleView.text = question.title
        textAnswerView = view.findViewById(R.id.edit_text_text_answer)
        recyclerQuestions = view.findViewById(R.id.recycler_view_question_points)

        val linearLayoutManager = LinearLayoutManager(context)

        if (question.type != QuestionType.Text) {
            textAnswerView.visibility = View.GONE
            recyclerQuestions.visibility = View.VISIBLE
            questionsAdapter = QuestionAdapter(false, true, question.type, ArrayList(question.points))
            recyclerQuestions.apply {
                layoutManager = linearLayoutManager
                adapter = questionsAdapter
            }
        } else {
            textAnswerView.visibility = View.VISIBLE
            recyclerQuestions.visibility = View.GONE
            textAnswerView = view.findViewById(R.id.edit_text_text_answer)
        }


        buttonNext = view.findViewById(R.id.button_answer)
        buttonNext.onClick { onNextClick() }
    }

    private fun onNextClick() {
        val passListener = activity as? QuestionPassListener
        if (passListener != null) {
            val answers = if (question.type == QuestionType.Text) {
                if (textAnswerView.text.trim().isEmpty()) {
                    return
                }
                listOf(textAnswerView.text.toString())
            } else {
                questionsAdapter.getAnswers().map { it.toString() }.toList()
            }

            passListener.onQuestionAnswer(category, index, answers)
        }
    }


    companion object {

        private val KEY_QUESTION = "huyo-moyo-key"
        private val KEY_CATEGORY = "huyo-moyo-category"
        private val KEY_INDEX = "huyo-moyo-index"

        fun newInstance(category: QuestionCategory, index: Int, question: Question) : QuestionPassFragment {
            val fragment = QuestionPassFragment()

            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putSerializable(KEY_CATEGORY, category)
            bundle.putSerializable(KEY_QUESTION, question)

            fragment.arguments = bundle
            return fragment

        }

    }

}