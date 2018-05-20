package com.blovote.surveys.ui.questions

import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blovote.R
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.QuestionType
import com.blovote.surveys.presentation.SurveyCreationPresenter
import com.blovote.surveys.ui.CreateSurveyActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

class EditQuestionFragment : Fragment() {

    private lateinit var surveyCreationPresenter: SurveyCreationPresenter

    private lateinit var category : QuestionCategory
    private lateinit var type : QuestionType
    private var position : Int = -1

    private lateinit var adapter: QuestionAdapter

    private lateinit var titleView : TextView
    private lateinit var spinnerView : Spinner
    private lateinit var recyclerPoints : RecyclerView
    private lateinit var addPointButton : ImageButton
    private lateinit var textViewTextDescr : TextView
    private lateinit var addQuestionButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_question_create_container, container, false)

        restoreArgs()
        setupUi(view)
        setupUx()
        return view
    }

    private fun restoreArgs() {
        category = arguments?.getSerializable(CATEGORY_KEY) as QuestionCategory
        position = arguments?.getInt(KEY_INDEX) as Int

        surveyCreationPresenter = (activity as CreateSurveyActivity).surveyCreationPresenter

        type = if (position == -1) QuestionType.SingleVariant
        else if (category == QuestionCategory.MainQuestion) {
            surveyCreationPresenter.mainQuestions()[position].type
        } else if (category == QuestionCategory.FilterQuestion) {
            surveyCreationPresenter.filterQuestions()[position].type
        } else {
            throw RuntimeException("Unexpected condition")
        }

    }

    private fun setupUi(view: View) {
        titleView = view.findViewById(R.id.title)
        spinnerView = view.findViewById(R.id.spinner_question_type)
        textViewTextDescr = view.findViewById(R.id.text_view_text_description)
        addPointButton = view.findViewById(R.id.button_add_point)
        addQuestionButton = view.findViewById(R.id.add_question_button)

        val linearLayoutManager = LinearLayoutManager(context)
        adapter = QuestionAdapter(true,category == QuestionCategory.FilterQuestion, type)

        recyclerPoints = view.findViewById(R.id.recycler_view_question_points)
        recyclerPoints.apply {
            layoutManager = linearLayoutManager
            adapter = this@EditQuestionFragment.adapter
        }


        handleTypeUpdate(type)

        tryFillFields()

    }

    private fun setupUx() {
        spinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val newType = QuestionType.values()[spinnerView.selectedItemPosition]
                handleTypeUpdate(newType)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        addPointButton.onClick {
            adapter.addPoint("")
        }

        addQuestionButton.onClick {
            hanleQuestionCreation()
        }
    }

    @UiThread
    private fun handleTypeUpdate(type: QuestionType) {
        adapter.updateType(type)

        if (type == QuestionType.Text) {
            textViewTextDescr.visibility = View.VISIBLE
            recyclerPoints.visibility = View.GONE
            addPointButton.visibility = View.GONE
        } else {
            textViewTextDescr.visibility = View.GONE
            recyclerPoints.visibility = View.VISIBLE
            addPointButton.visibility = View.VISIBLE
        }
    }

    private fun tryFillFields() {
        val question = when {
            category == QuestionCategory.FilterQuestion && position > 0 && position < surveyCreationPresenter.filterQuestions().size -> {
                surveyCreationPresenter.filterQuestions()[position]
            }
            category == QuestionCategory.MainQuestion && position > 0 && position < surveyCreationPresenter.mainQuestions().size -> {
                surveyCreationPresenter.mainQuestions()[position]
            }
            else -> null
        }

        if (question != null) {
            type = question.type
            handleTypeUpdate(type)
            adapter.setPoints(question.points)
            adapter.updateType(question.type)
        }

        titleView.setText(question?.title ?: "", TextView.BufferType.EDITABLE)
        //TODO: порядок в енамчике и ресурсах может ебать как разъехаться
        spinnerView.setSelection((question?.type ?: QuestionType.SingleVariant).ordinal)
    }


    private fun hanleQuestionCreation() {
        var isComplete = true
        var notCompleteMessage = ""

        val type = QuestionType.values()[spinnerView.selectedItemPosition]
        val points = adapter.getPoints()
        if (points.size == 0) {
            isComplete = false
            notCompleteMessage = getString(R.string.msg_question_no_points)
        }
        for (i in 0 until points.size) {
            if (points[i].isEmpty()) {
                isComplete = false
                notCompleteMessage = String.format(getString(R.string.msg_not_complete_point), i + 1)
            }
        }


        val title = titleView.text.trim()
        if (title.isEmpty()) {
            isComplete = false
            notCompleteMessage = getString(R.string.msg_not_complete_question_title)
        }

        if (!isComplete && context != null) {
            AlertDialog.Builder(context!!).setTitle(getString(R.string.msg_complete_question_title))
                    .setMessage(notCompleteMessage)
                    .setPositiveButton(getString(R.string.ok)) { dialogInterface, _ -> dialogInterface.cancel()}
                    .create().show()
        } else {
            val question = Question(title.toString(), type, points, adapter.getAnswers())
            surveyCreationPresenter.onRequestQuestionAdd(category, question)
            (activity as? CreateSurveyActivity)?.onQuestionAdded()
        }
    }


    companion object {

        private val CATEGORY_KEY = "category"
        private val KEY_INDEX = "index"

        fun newInstance(questionCategory: QuestionCategory, position: Int = -1) : EditQuestionFragment {
            val fragment = EditQuestionFragment()
            val args = Bundle()

            args.putSerializable(CATEGORY_KEY, questionCategory)
            args.putInt(KEY_INDEX, position)

            fragment.arguments = args

            return fragment
        }

    }
}