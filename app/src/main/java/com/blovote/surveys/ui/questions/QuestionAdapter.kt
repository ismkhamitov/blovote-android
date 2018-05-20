package com.blovote.surveys.ui.questions

import android.support.annotation.UiThread
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blovote.R
import com.blovote.surveys.data.entities.QuestionType
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick

class QuestionAdapter(private val editMode : Boolean = true,
                      private val hasRightAnswer: Boolean = false,
                      private var type : QuestionType = QuestionType.SingleVariant,
                      private var points : MutableList<String> = mutableListOf(""),
                      private var answs: MutableList<Int> = ArrayList())

    : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {


    init {
        if (type == QuestionType.SingleVariant && answs.size > 1) {
            throw RuntimeException("Single variant questions cannot have more than 1 right answer")
        }
    }

    private var lastRadioButton : RadioButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_question_point, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView

        val radioView = view.findViewById<RadioButton>(R.id.radio_point)
        radioView.visibility = if (type == QuestionType.SingleVariant) View.VISIBLE else View.GONE
        radioView.isEnabled = !editMode || hasRightAnswer
        radioView.isChecked = answs.contains(position)
        radioView.onClick {
            checkType(QuestionType.SingleVariant)
            if (answs.size > 0) {
                lastRadioButton?.isChecked = false
            }
            radioView.isChecked = true
            lastRadioButton = radioView
            answs = mutableListOf(holder.adapterPosition)
        }

        val checkView = view.findViewById<CheckBox>(R.id.check_point)
        checkView.visibility = if (type == QuestionType.ManyVariants) View.VISIBLE else View.GONE
        checkView.isEnabled = !editMode || hasRightAnswer
        checkView.isChecked = answs.contains(position)
        checkView.setOnCheckedChangeListener({button, checked ->
            if (checked) {
                answs.add(holder.adapterPosition)
                answs.sort()
            } else {
                answs.remove(holder.adapterPosition)
            }
        })

        val editText = view.findViewById<EditText>(R.id.text_point)
        editText.setText(points[position], TextView.BufferType.EDITABLE)
        setEditable(editText, editMode)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                points[holder.adapterPosition] = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        val deleteButton = view.findViewById<ImageButton>(R.id.point_delete)
        deleteButton.visibility = if (editMode) View.VISIBLE else View.GONE
        deleteButton.onClick { requestPointDelete(holder.adapterPosition) }
    }

    private fun setEditable(editText: EditText, editMode: Boolean) {
        editText.isClickable = editMode
        editText.isFocusable = editMode
        editText.isCursorVisible = editMode
        editText.isFocusableInTouchMode = editMode
    }


    @UiThread
    fun updateType(type: QuestionType) {
        if (type == this.type) {
            return
        }


        answs.clear()
        this.type = type

        notifyDataSetChanged()
    }

    fun setPoints(newPoints: List<String>) {
        points = ArrayList(newPoints)
        launch(UI) {
            notifyDataSetChanged()
        }
    }

    fun getPoints() : List<String> {
        return points
    }

    fun getAnswers() : List<Int> {
        return answs
    }

    fun addPoint(point: String) {
        points.add(point)
        launch(UI) {
            notifyItemRangeInserted(points.size - 1, 1)
        }
    }

    fun requestPointDelete(position: Int) {
        if (position >= 0 && position < points.size) {
            points.removeAt(position)
            notifyItemRangeRemoved(position, 1)
        }
    }


    private fun checkType(type: QuestionType) {
        if (this.type != type) {
            throw RuntimeException("Expected ${type}, but was ${this.type}")
        }
    }

    /************************************* Inner classes *******************************/

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}