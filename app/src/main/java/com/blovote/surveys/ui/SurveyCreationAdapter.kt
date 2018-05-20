package com.blovote.surveys.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.presentation.QuestionCreationCallback
import com.blovote.surveys.ui.questions.QuestionTitleClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.concurrent.atomic.AtomicReference

class SurveyCreationAdapter(questionsProvider: Observable<List<Question>>,
                            val questionsCategory: QuestionCategory,
                            val questionClickListener: QuestionTitleClickListener,
                            val questionCreationCallback: QuestionCreationCallback)

    : RecyclerView.Adapter<SurveyCreationAdapter.ViewHolder>() {

    val disposable = AtomicReference<Disposable>()
    var currList : List<Question> = ArrayList()


    init {
        DisposableHelper.set(disposable, questionsProvider
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newList ->
                    val diff = DiffUtil.calculateDiff(QuestionsDiffUtil(currList, newList))
                    currList = newList
                    diff.dispatchUpdatesTo(this)
                })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_question_title_card,
                parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleView = holder.itemView.findViewById<TextView>(R.id.question_title)
        val deleteView = holder.itemView.findViewById<View>(R.id.question_delete)

        val question = currList[position]

        titleView.text = question.title
        titleView.onClick { questionClickListener.onQuestionTitleClick(questionsCategory, position) }

        deleteView.onClick { questionCreationCallback.onRequestQuestionDelete(questionsCategory, position) }
    }



    fun dispose() {
        DisposableHelper.dispose(disposable)
    }



    /******************************** Inner classes ****************************************/

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class QuestionsDiffUtil(val currentList: List<Question>, val nextList: List<Question>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(oldItemPosition, newItemPosition)
        }

        override fun getOldListSize(): Int {
            return currentList.size
        }

        override fun getNewListSize(): Int {
            return nextList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val curr = currentList[oldItemPosition]
            val next = nextList[newItemPosition]
            val pred = curr.title == next.title && curr.points.size == next.points.size
            return if (pred) {
                for (i in 0 until curr.points.size) {
                    if (curr.points[i] != next.points[i]) return false
                }

                true
            } else false
        }
    }
}