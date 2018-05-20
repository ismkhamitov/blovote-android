package com.blovote.app

import com.blovote.account.di.AccountInfoModule
import com.blovote.contracts.di.ContractsModule
import com.blovote.surveys.di.SurveysModule
import com.blovote.surveys.ui.CreateSurveyActivity
import com.blovote.surveys.ui.MainSurveysActivity
import com.blovote.web3.di.Web3Module
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AppModule::class,
    Web3Module::class,
    SurveysModule::class,
    ContractsModule::class,
    AccountInfoModule::class
])
interface AppComponent {

    fun inject(mainSurveysActivity: MainSurveysActivity)

    fun inject(mainSurveysActivity: CreateSurveyActivity)

}