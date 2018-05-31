package com.blovote.account.data

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Single
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.core.DefaultBlockParameterName
import java.io.File
import java.math.BigInteger

class AccountStorageImpl(val context : Context) : AccountStorage {

    val prefs : SharedPreferences = context.getSharedPreferences(ACCOUNT_PREFS, Context.MODE_PRIVATE)

    override fun isWalletExists() : Boolean {
        return prefs.contains(WALLET_FILENAME_KEY)
    }

    override fun createNewWallet(password: String) : Credentials {
        val walletFile = File(WalletUtils.generateFullNewWalletFile(password, context.filesDir))
        updatePrefs(password, walletFile.name)

        return WalletUtils.loadCredentials(password, walletFile)
    }

    override fun loadCredentials() : Credentials {
        val password = prefs.getString(PASSWORD_KEY, null) ?: throw RuntimeException("Password does not set")
        val filename : String = prefs.getString(WALLET_FILENAME_KEY, null) ?: throw RuntimeException("Wallet file does not exists")
        return WalletUtils.loadCredentials(password, filename)
    }

    override fun updateCredentials(password : String, walletFile : File) : Boolean {
        try {
            WalletUtils.loadCredentials(password, walletFile)
        } catch (e : Exception) {
            return false;
        }

        val oldWalletFilename = prefs.getString(WALLET_FILENAME_KEY, null)
        if (oldWalletFilename != null) {
            val file = File(context.filesDir, oldWalletFilename)
            if (file.exists()) {
                file.delete()
            }
        }

        val newFilename = walletFile.name
        val newWalletFile = File(context.filesDir, newFilename)
        walletFile.copyTo(newWalletFile)
        updatePrefs(password, newFilename)

        return true
    }


    private fun updatePrefs(password: String, filename: String) {
        prefs.edit()
                .putString(PASSWORD_KEY, password)
                .putString(WALLET_FILENAME_KEY, filename)
                .apply()
    }


    companion object {
        private val ACCOUNT_PREFS = "account_prefs"
        private val PASSWORD_KEY = "password"
        private val WALLET_FILENAME_KEY = "wallet_filename"
    }

}