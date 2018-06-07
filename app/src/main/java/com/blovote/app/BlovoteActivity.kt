package com.blovote.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blovote.account.data.AccountStorage
import com.blovote.app.wizard.WizardActivity
import javax.inject.Inject

abstract class BlovoteActivity : AppCompatActivity() {

    @Inject
    lateinit var accountStorage: AccountStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        if (needToInitWallet()) {
            val intent = Intent(this, WizardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }


    private fun needToInitWallet() : Boolean {
        return !accountStorage.isWalletExists() || !accountStorage.areCredentialsLoaded()
    }
}