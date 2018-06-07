package com.blovote.app

import com.blovote.account.di.AccountInfoModule
import com.blovote.app.wizard.WizardActivity
import com.blovote.contracts.di.ContractsModule
import com.blovote.surveys.di.SurveysModule
import com.blovote.surveys.ui.MainSurveysActivity
import com.blovote.surveys.ui.creation.CreateSurveyActivity
import com.blovote.surveys.ui.monitoring.MySurveyActivity
import com.blovote.surveys.ui.monitoring.MySurveysFragment
import com.blovote.surveys.ui.passing.SurveyActivity
import com.blovote.surveys.ui.passing.SurveyDetailsFragment
import com.blovote.wallet.di.WalletModule
import com.blovote.wallet.ui.WalletControlFragment
import com.blovote.wallet.ui.WalletInitFragment
import com.blovote.web3.di.Web3Module
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AppModule::class,
    Web3Module::class,
    SurveysModule::class,
    ContractsModule::class,
    AccountInfoModule::class,
    WalletModule::class
])
interface AppComponent {

    fun inject(mainSurveysActivity: MainSurveysActivity)

    fun inject(mainSurveysActivity: CreateSurveyActivity)
    
    fun inject(surveyActivity: SurveyActivity)

    fun inject(surveyDetailsFragment: SurveyDetailsFragment)

    fun inject(walletControlFragment: WalletControlFragment)

    fun inject(mySurveysFragment: MySurveysFragment)

    fun inject(mySurveyActivity: MySurveyActivity)

    fun inject(walletInitFragment: WalletInitFragment)

    fun inject(wizardActivity: WizardActivity)

    fun inject(blovoteActivity: BlovoteActivity)

}