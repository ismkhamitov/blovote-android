package com.blovote.account.data

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Single
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.InputStream
import java.util.*

class AccountStorageImpl(val context : Context) : AccountStorage {

    private val prefs : SharedPreferences = context.getSharedPreferences(ACCOUNT_PREFS, Context.MODE_PRIVATE)

    private lateinit var credentials: Credentials

    override fun isWalletExists() : Boolean {
        val walletFile = getWalletFile()
        return walletFile != null && walletFile.exists()
    }

    override fun createNewWallet(password: String) : Single<Credentials> {
        return Single.create {
            try {
                val walletFile = File(WalletUtils.generateFullNewWalletFile(password, context.filesDir))
                removeCurrentWallet()
                saveWallet(password, walletFile.name)
                credentials = WalletUtils.loadCredentials(password, getWalletFile())
                it.onSuccess(credentials)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun updateWallet(password : String, walletFileStream : InputStream) : Single<Boolean> {
        return Single.create {
            removeCurrentWallet()
            val newFilename = UUID.randomUUID().toString()
            val newWalletFile = File(context.filesDir, newFilename)
            val os = newWalletFile.outputStream()
            var closed = false
            try {

                walletFileStream.copyTo(os)
                os.close()
                closed = true

                try {
                    WalletUtils.loadCredentials(password, newWalletFile)
                } catch (e: Exception) {
                    it.onSuccess(false)
                    return@create
                }

                saveWallet(password, newFilename)

                it.onSuccess(true)

            } catch (e: Exception) {
                it.onError(e)
            } finally {
                if (!closed) {
                    os.close()
                }
            }
        }
    }


    override fun areCredentialsLoaded(): Boolean {
        return this::credentials.isInitialized
    }

    override fun loadCredentials(): Single<Credentials> {
        return Single.create {
            try {
                val password = prefs.getString(PASSWORD_KEY, null) ?: throw RuntimeException("Password does not set")
                val filename : String = getWalletFile()?.canonicalPath ?: throw RuntimeException("Wallet file does not exists")
                credentials = WalletUtils.loadCredentials(password, filename)
                it.onSuccess(credentials)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun getCredentials() : Credentials {
        return credentials
    }


    private fun saveWallet(password: String, filename: String) {
        prefs.edit()
                .putString(PASSWORD_KEY, password)
                .putString(WALLET_FILENAME_KEY, filename)
                .apply()
    }

    private fun removeCurrentWallet() : Boolean {
        val walletFile = getWalletFile()
        val deleteRes = walletFile == null || !walletFile.exists() || walletFile.delete()
        prefs.edit().remove(WALLET_FILENAME_KEY).apply()

        return deleteRes
    }

    private fun getWalletFile() : File? {
        if (!prefs.contains(WALLET_FILENAME_KEY)) {
            return null
        }

        return File(context.filesDir, prefs.getString(WALLET_FILENAME_KEY, null))
    }

    companion object {
        private val ACCOUNT_PREFS = "account_prefs"
        private val PASSWORD_KEY = "password"
        private val WALLET_FILENAME_KEY = "wallet_filename"
    }

}