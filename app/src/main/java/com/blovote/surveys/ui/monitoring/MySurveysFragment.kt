package com.blovote.surveys.ui.monitoring

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blovote.R
import com.blovote.app.App
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.ui.SurveysAdapter
import com.blovote.surveys.ui.passing.SurveyActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MySurveysFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : SurveysAdapter
    private lateinit var layoutManager : RecyclerView.LayoutManager

    private var disposable = CompositeDisposable()

    @Inject
    lateinit var surveysInteractor: SurveysInteractor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_my_surveys, container, false)
        App.appComponent.inject(this)
        setupUi(view)
        setupData()
        return view
    }

    private fun setupUi(view: View) {
        val context = this.context
        if (context != null) {
            layoutManager = LinearLayoutManager(this.context)
            adapter = SurveysAdapter(surveysInteractor)
            disposable.add(adapter.observeSurveyClick()
                    .subscribe { onSurveyClicked(it.first, it.second) })

            recyclerView = view.findViewById(R.id.recycler_your_surveys)
            recyclerView.apply {
                layoutManager = this@MySurveysFragment.layoutManager
                adapter = this@MySurveysFragment.adapter
            }
        }
    }


    private fun setupData() {
        adapter.startObservingSurveys(surveysInteractor.observeMySurveys(this))
    }


    private fun onSurveyClicked(address: String, index: Int) {
        val context = this.context
        if (context != null) {
            startActivity(SurveyActivity.getStartIntent(context, address, index))
        }
    }


    companion object {

        fun newInstance() : MySurveysFragment {
            return MySurveysFragment()
        }

    }
}