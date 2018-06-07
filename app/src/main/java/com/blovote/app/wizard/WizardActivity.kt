package com.blovote.app.wizard

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.blovote.R
import com.blovote.account.data.AccountStorage
import com.blovote.app.App
import com.blovote.app.CommonProgressFragment
import com.blovote.surveys.ui.MainSurveysActivity
import com.blovote.wallet.ui.WalletInitFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class WizardActivity : AppCompatActivity(), WizardStepsListener {


    @Inject
    lateinit var accountStorage: AccountStorage


    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)
        showNextStep()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun onWizardStepFinished(wizardStep: WizardStep) {
        launch (UI) {
            showNextStep()
        }
    }


    private fun needToShowWizardStep(wizardStep: WizardStep) : Boolean {
        return when (wizardStep) {
            WizardStep.InitWallet -> {
                !accountStorage.isWalletExists()
            }
            WizardStep.LoadWallet -> {
                !accountStorage.areCredentialsLoaded()
            }
            else -> throw IllegalStateException()
        }
    }



    private fun showNextStep() {
        for (step in WizardStep.values()) {
            if (needToShowWizardStep(step)) {
                showWizardStep(step)
                return
            }
        }

        goToMainActivity()
    }


    private fun showWizardStep(wizardStep: WizardStep) {
        when (wizardStep) {
            WizardStep.InitWallet -> {
                showFragment(WalletInitFragment.getInstance())
            }
            WizardStep.LoadWallet -> {
                loadWallet()
            }
            else -> throw IllegalStateException()
        }
    }

    private fun loadWallet() {
        val fragment = CommonProgressFragment.newInstance(getString(R.string.msg_loading_wallet))
        showFragment(fragment)
        disposable.add(accountStorage.loadCredentials()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _, throwable ->
                    if (throwable != null) {
                        throw IllegalStateException()
                    } else {
                        this@WizardActivity.onWizardStepFinished(WizardStep.LoadWallet)
                    }
                }))
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment, null)
                .commit()
    }


    private fun goToMainActivity() {
        val intent = Intent(this, MainSurveysActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}