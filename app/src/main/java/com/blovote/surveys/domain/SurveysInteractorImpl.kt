package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Completable
import io.reactivex.Observable
import org.web3j.protocol.Web3j
import java.math.BigInteger

class SurveysInteractorImpl(val surveysRepository: SurveysRepository, web3j : Web3j) : SurveysInteractor {

    override fun getExistingSurveys(): List<Survey> {
        return surveysRepository.getAllSurveys()
    }

    override fun observeExistingSurveys(lifecycleOwner: LifecycleOwner): Observable<List<Survey>> {
        return surveysRepository.observeAllSurveys(lifecycleOwner)
    }

    override fun updateSurveys(): List<Survey> {
        return surveysRepository.updateSurveys()
    }

    override fun createSurvey(title: String, requiredRespondentsCnt: Int,
                              rewardSize: BigInteger,
                              filterQuestions: List<Question>,
                              mainQuestions: List<Question>) : Completable {
        return surveysRepository.createSurvey(title, requiredRespondentsCnt, rewardSize, filterQuestions, mainQuestions)
    }
}