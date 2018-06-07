package com.blovote.account.data

import android.content.Context
import io.reactivex.Single
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.InputStream


class MockAccountStorage(val context: Context) : AccountStorage {

    private var creds: Credentials? = null


    override fun areCredentialsLoaded(): Boolean {
        return creds != null
    }

    override fun isWalletExists(): Boolean {
        return true
    }

    override fun createNewWallet(password: String): Single<Credentials> {
        throw NotImplementedError()
    }

    override fun getCredentials(): Credentials {
        if (creds == null) {
            throw IllegalStateException("Credentials are not loaded")
        }
        return creds!!
    }

    override fun updateWallet(password: String, walletFileStream: InputStream): Single<Boolean> {
        throw NotImplementedError()
    }

    override fun loadCredentials(): Single<Credentials> {
        return Single.create {
            if (creds == null) {
                val targetFile = File(context.filesDir, "key")
                val stream = context.assets.open("key")

                if (targetFile.exists()) {
                    targetFile.delete()
                }

                val os = targetFile.outputStream()

                try {
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (true) {
                        len = stream.read(buffer)
                        if (len == -1) break

                        os.write(buffer, 0, len)
                    }
                    creds = WalletUtils.loadCredentials(PASSWORD, targetFile)
                } catch (e: Exception) {
                    it.onError(e)
                } finally {
                    os.close()
                }
            }

            it.onSuccess(creds!!)
        }
    }

    companion object {
        private val PASSWORD = "Ramovi07"
        private val KEYSTORE = "/Users/ismail/Library/Ethereum/keystore/"
        private val MAIN_WALLET_FILE = KEYSTORE + "UTC--2018-04-22T09-58-09.850325000Z--e12fb268f1ab83c738e48191d1d417d3e907e91e"
    }
}