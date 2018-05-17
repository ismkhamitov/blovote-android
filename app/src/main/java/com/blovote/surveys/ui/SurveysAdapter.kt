package com.blovote.surveys.ui

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blovote.R
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicReference

class SurveysAdapter(val surveysInteractor: SurveysInteractor) : RecyclerView.Adapter<SurveysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val disposableRef : AtomicReference<Disposable> = AtomicReference()
    private val surveys = SortedList<Survey>(Survey::class.java, object: SortedList.Callback<Survey>() {

        override fun areItemsTheSame(item1: Survey?, item2: Survey?): Boolean {
            return item1?.index == item2?.index
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            launch(UI) {
                notifyItemMoved(fromPosition, toPosition)
            }
        }

        override fun onChanged(position: Int, count: Int) {
            launch(UI) {
                notifyItemRangeChanged(position, count)
            }
        }

        override fun onInserted(position: Int, count: Int) {
            launch(UI) {
                notifyItemRangeInserted(position, count)
            }
        }

        override fun onRemoved(position: Int, count: Int) {
            launch(UI) {
                notifyItemRangeRemoved(position, count)
            }
        }

        override fun compare(o1: Survey?, o2: Survey?): Int {
            if (o1 == null) {
                return if (o2 == null) 0 else -1
            }
            if (o2 == null) return 1

            return o2.index - o1.index
        }

        override fun areContentsTheSame(oldItem: Survey?, newItem: Survey?): Boolean {
            return oldItem?.index == newItem?.index
        }
    })

    fun startObservingSurveys(lifecycleOwner: LifecycleOwner) {
        DisposableHelper.set(disposableRef, surveysInteractor.observeExistingSurveys(lifecycleOwner)
                .observeOn(Schedulers.computation())
                .subscribe({ surveysList ->
                    surveys.clear()
                    surveys.replaceAll(surveysList)
                }))
    }

    fun stopObservingSurveys() {
        DisposableHelper.dispose(disposableRef)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_survey_card,
                parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = surveys.size()


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleTextView = holder.itemView.findViewById<TextView>(R.id.survey_title)
        val rewardTextView = holder.itemView.findViewById<TextView>(R.id.reward_size)
        val questionsDescrTextView = holder.itemView.findViewById<TextView>(R.id.questions_description)

        val survey = surveys.get(position)
        titleTextView.text = survey.title
        rewardTextView.text = getRewardSizeText(survey.rewardSize)
        questionsDescrTextView.text = holder.itemView.context.getString(R.string.questions_descr,
                survey.questionsCount.toString())
    }



    private fun getRewardSizeText(rewardString: String) : String {
        val rewardSize = BigDecimal(rewardString)
        if (rewardSize.compareTo(BigDecimal.TEN.pow(3)) < 0) {
            return String.format("%s wei", rewardString)
        } else {
            var value : Double
            var unit : String
            when {
                rewardSize.compareTo(BigDecimal.TEN.pow(6)) < 0 -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(3)).toDouble()
                    unit = "Kwei"
                }
                rewardSize.compareTo(BigDecimal.TEN.pow(9)) < 0 -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(6)).toDouble()
                    unit = "Mwei"
                }
                rewardSize.compareTo(BigDecimal.TEN.pow(12)) < 0 -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(9)).toDouble()
                    unit = "Gwei"
                }
                rewardSize.compareTo(BigDecimal.TEN.pow(15)) < 0 -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(12)).toDouble()
                    unit = "microether"
                }
                rewardSize.compareTo(BigDecimal.TEN.pow(18)) < 0 -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(15)).toDouble()
                    unit = "milliether"
                }
                else -> {
                    value = rewardSize.divide(BigDecimal.TEN.pow(18)).toDouble()
                    unit = "ether"
                }
            }

            return String.format("%1s,.3f %s", value, unit)
        }

    }

}