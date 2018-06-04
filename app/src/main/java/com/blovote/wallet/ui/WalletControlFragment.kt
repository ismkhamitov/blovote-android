package com.blovote.wallet.ui

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blovote.R
import com.blovote.account.domain.AccountInteractor
import com.blovote.app.App
import com.blovote.surveys.ui.toEtherString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.web3j.protocol.Web3j
import org.web3j.utils.Convert
import java.math.BigDecimal
import javax.inject.Inject

class WalletControlFragment : Fragment() {


    @Inject
    lateinit var web3j : Web3j

    @Inject
    lateinit var accountInteractor: AccountInteractor

    private lateinit var buttonSend : Button
    private lateinit var progressBarSending : ProgressBar


    private var disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_wallet_screen, container, false)
        App.appComponent.inject(this)

        return view
    }

    override fun onResume() {
        super.onResume()
        setupUi()
        setupUx()
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    private fun setupUi() {
        val view = this.view ?: return
        val viewAddress = view.findViewById<TextView>(R.id.text_view_your_address)
        val viewBalance = view.findViewById<TextView>(R.id.text_view_balance)

        viewAddress.text = accountInteractor.getAddress()

        disposable.add(accountInteractor.getBalance()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { value, throwable ->
                    if (throwable != null) {
                        AlertDialog.Builder(context)
                                .setMessage("Error loading balance")
                                .show()
                    } else {
                        viewBalance.text = value.toEtherString()
                    }
                })
    }

    private fun setupUx() {
        val view = this.view ?: return
        val targetAddressView = view.findViewById<EditText>(R.id.text_view_recipient_address)
        val sendAmountView = view.findViewById<TextView>(R.id.edit_text_transfer_size)
        buttonSend = view.findViewById(R.id.button_send)
        progressBarSending = view.findViewById(R.id.progress_bar_sending)
        val spinnerUnit = view.findViewById<Spinner>(R.id.spinner_ether_unit)

        buttonSend.onClick {
            val targetAddress = targetAddressView.text.trim()
            val sizeText = sendAmountView.text.toString().trim()
            val size = if (sizeText.isEmpty()) 0L else sizeText.toLong()


            if (size > 0L) {
                val exp = 18 - spinnerUnit.selectedItemPosition * 3

                val actualSize = BigDecimal(size).divide(BigDecimal.TEN.pow(exp))
                AlertDialog.Builder(this@WalletControlFragment.context)
                        .setMessage(String.format("Send %s Eth to address %s?", actualSize, targetAddress))
                        .setPositiveButton("Yes", { _, _ -> onSendEther(targetAddress.toString(), actualSize) })
                        .show()
            }
        }
    }


    private fun onSendEther(address: String, amount: BigDecimal) {
        progressBarSending.visibility = View.VISIBLE
        buttonSend.visibility = View.GONE

        disposable.add(accountInteractor.sendEther(address, amount, Convert.Unit.ETHER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progressBarSending.visibility = View.GONE
                    buttonSend.visibility = View.VISIBLE
                    AlertDialog.Builder(context)
                            .setMessage(getString(R.string.message_ether_sent))
                            .setPositiveButton(R.string.ok, { _, _ -> })
                            .show()
                }, {
                    progressBarSending.visibility = View.GONE
                    buttonSend.visibility = View.VISIBLE
                    AlertDialog.Builder(context)
                            .setMessage(getString(R.string.message_ether_sending_error))
                            .setPositiveButton(R.string.ok, { _, _ -> })
                            .show()
                }))
    }


    companion object {

        fun newInstance() : WalletControlFragment {
            return WalletControlFragment()
        }

    }

}