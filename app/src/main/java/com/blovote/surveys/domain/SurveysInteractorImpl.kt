package com.blovote.surveys.domain

import android.arch.lifecycle.LifecycleOwner
import com.blovote.surveys.data.entities.Question
import com.blovote.surveys.data.entities.QuestionCategory
import com.blovote.surveys.data.entities.Survey
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.web3j.protocol.Web3j
import java.math.BigInteger

class SurveysInteractorImpl(val surveysRepository: SurveysRepository, web3j : Web3j) : SurveysInteractor {

    override fun getSurvey(address: String): Single<Survey> {
        return surveysRepository.getSurvey(address).subscribeOn(Schedulers.computation())
    }

    override fun getExistingSurveys(): List<Survey> {
        return surveysRepository.getAllSurveys()
    }

    override fun observeExistingSurveys(lifecycleOwner: LifecycleOwner): Observable<List<Survey>> {
        return surveysRepository.observeAllSurveys(lifecycleOwner)
    }

    override fun updateSurveyInfo(survey: Survey): Single<Survey> {
        return surveysRepository.updateSurveyInfo(survey).subscribeOn(Schedulers.computation())
    }

    override fun updateSurveyQuestionInfo(survey: Survey, category: QuestionCategory, index: Int): Single<Survey> {
        return surveysRepository.updateSurveyQuestionInfo(survey, category, index).subscribeOn(Schedulers.computation())
    }

    override fun checkAnswer(survey: Survey, questionIndex: Int, answers: List<String>) : Single<Boolean> {
        return surveysRepository.checkAnswer(survey, questionIndex, answers).subscribeOn(Schedulers.computation())
    }

    override fun uploadAnswer(survey: Survey, questionIndex: Int, answers: List<String>): Completable {
        return surveysRepository.uploadAnswer(survey, questionIndex, answers).subscribeOn(Schedulers.computation())
    }

    override fun requestSurveysUpdate(): Completable {
        return Completable.create({
            try {
                surveysRepository.updateSurveys()
            } catch (e : Exception) {
                e.printStackTrace()
                it.onError(e)
            }

            it.onComplete()
        })
    }

    override fun createSurvey(title: String, requiredRespondentsCnt: Int,
                              rewardSize: BigInteger,
                              filterQuestions: List<Question>,
                              mainQuestions: List<Question>) : Completable {
        return Completable.create({
            try {
                surveysRepository.createSurvey(title, requiredRespondentsCnt, rewardSize, filterQuestions, mainQuestions)
            } catch (e: Exception) {
                e.printStackTrace()
                it.onError(e)
            }
            it.onComplete()
        }).subscribeOn(Schedulers.computation())
    }
}