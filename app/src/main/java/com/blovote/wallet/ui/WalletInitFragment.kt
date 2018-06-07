package com.blovote.wallet.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.blovote.R
import com.blovote.app.App
import com.blovote.app.wizard.WizardStep
import com.blovote.app.wizard.WizardStepsListener
import com.blovote.wallet.presentation.WalletPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject


class WalletInitFragment : Fragment() {

    @Inject
    lateinit var walletPresenter: WalletPresenter

    private lateinit var buttonCreateWallet: Button
    private lateinit var buttonUseExistingWallet: Button
    private lateinit var buttonNext: Button
    private lateinit var editTextPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_wallet_init_fragment, container, false)

        App.appComponent.inject(this)
        setupUi(view)

        return view
    }

    private fun setupUi(view: View) {
        buttonNext = view.findViewById(R.id.button_go_next)
        editTextPassword = view.findViewById(R.id.edit_text_password)

        buttonCreateWallet = view.findViewById<Button>(R.id.button_create_wallet)
        buttonCreateWallet.onClick {
            requestPassword(WalletInitType.New)
        }

        buttonUseExistingWallet = view.findViewById<Button>(R.id.button_use_existing_wallet)

        buttonUseExistingWallet.onClick {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            walletPresenter.setSelectedFile(data.data)
            requestPassword(WalletInitType.Existing)
        }
    }


    private fun requestPassword(initType: WalletInitType) {
        buttonCreateWallet.visibility = View.GONE
        buttonUseExistingWallet.visibility = View.GONE

        buttonNext.visibility = View.VISIBLE
        editTextPassword.visibility = View.VISIBLE

        buttonNext.onClick {
            if (!checkEnteredPassword()) {
                return@onClick
            }

            val dialog = showProgressDialog()
            if (initType == WalletInitType.Existing) {
                val activity = activity
                if (activity != null) {
                    walletPresenter.saveExistingWallet(activity.contentResolver, editTextPassword.text.toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ result, throwable ->
                                dialog?.dismiss()
                                if (throwable != null || !result) {
                                    showErrorDialog()
                                } else {
                                    notifyInitFinished()
                                }
                            })
                } else {
                    dialog?.dismiss()
                }
            } else {
                walletPresenter.createWallet(editTextPassword.text.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            dialog?.dismiss()
                            notifyInitFinished()
                        }, {
                            dialog?.dismiss()
                            showErrorDialog()
                        })
            }
        }
    }

    private fun checkEnteredPassword() : Boolean {
        return editTextPassword.text.isNotEmpty()
    }


    private fun showProgressDialog() : AlertDialog? {
        val activity = activity
        if (activity != null) {
            return AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setView(R.layout.layout_progress_dialog)
                    .show()
        }

        return null
    }

    private fun showErrorDialog() {
        val activity = activity
        if (activity != null) {
            AlertDialog.Builder(activity)
                    .setMessage(getString(R.string.error_init_wallet))
                    .show()
        }
    }

    private fun notifyInitFinished() {
        (activity as? WizardStepsListener)?.onWizardStepFinished(WizardStep.InitWallet)
    }

    companion object {

        private val READ_REQUEST_CODE = 42


        fun getInstance() : WalletInitFragment {
            return WalletInitFragment()
        }

    }

    enum class WalletInitType {

        New,
        Existing

    }
}