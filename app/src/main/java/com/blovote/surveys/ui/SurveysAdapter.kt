package com.blovote.surveys.ui

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicReference

class SurveysAdapter(val surveysInteractor: SurveysInteractor) : RecyclerView.Adapter<SurveysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val surveyClicksObservable = PublishSubject.create<Pair<String, Int>>()

    private val disposableRef : AtomicReference<Disposable> = AtomicReference()

    private var surveysList : List<Survey> = ArrayList()

    fun startObservingSurveys(surveysObservable: Observable<List<Survey>>) {
        DisposableHelper.set(disposableRef, surveysObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newSurveysList ->
                    val diff = DiffUtil.calculateDiff(SurveysDiffUtil(surveysList, newSurveysList))
                    surveysList = newSurveysList
                    diff.dispatchUpdatesTo(this)
                }))
    }

    fun stopObservingSurveys() {
        DisposableHelper.dispose(disposableRef)
    }

    fun observeSurveyClick() : Observable<Pair<String, Int>> {
        return surveyClicksObservable
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_survey_card,
                parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = surveysList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleTextView = holder.itemView.findViewById<TextView>(R.id.survey_title)
        val rewardTextView = holder.itemView.findViewById<TextView>(R.id.reward_size)
        val questionsDescrTextView = holder.itemView.findViewById<TextView>(R.id.questions_description)

        val survey = surveysList[position]
        titleTextView.text = survey.title
        rewardTextView.text = BigInteger(survey.rewardSize).toReadableString()
        questionsDescrTextView.text = holder.itemView.context.getString(R.string.questions_descr,
                (survey.filterQuestionsCount + survey.questionsCount).toString())

        holder.itemView.findViewById<CardView>(R.id.survey_card_view).onClick {
            surveyClicksObservable.onNext(Pair(survey.address, survey.index))
        }
    }

    /************************************ Inner classes *************************************/

    class SurveysDiffUtil(val currentList: List<Survey>, val nextList: List<Survey>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val curr = currentList[oldItemPosition]
            val next = nextList[newItemPosition]
            return curr.index == next.index
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
            return curr.title == next.title && curr.index == next.index
                        && curr.filterQuestionsCount + curr.questionsCount == next.filterQuestionsCount + next.questionsCount
        }
    }



}