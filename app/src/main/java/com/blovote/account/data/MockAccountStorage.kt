package com.blovote.account.data

import android.content.Context
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File


class MockAccountStorage(val context: Context) : AccountStorage {

    var creds: Credentials

    init {
        var targetFile = File(context.filesDir, "key")
        val stream = context.assets.open("key")

        if (targetFile.exists()) {
            targetFile.delete()
        }

        val os = targetFile.outputStream()
        val buffer = ByteArray(1024)
        var len : Int
        while (true) {
            len = stream.read(buffer)
            if (len == -1) break

            os.write(buffer, 0, len)
        }
        os.close()

        creds = WalletUtils.loadCredentials(PASSWORD, targetFile)
    }

    override fun isWalletExists(): Boolean {
        return true
    }

    override fun createNewWallet(password: String): Credentials {
        throw NotImplementedError()
    }

    override fun loadCredentials(): Credentials {
        return creds
    }

    override fun updateCredentials(password: String, walletFile: File): Boolean {
        throw NotImplementedError()
    }

    companion object {
        private val PASSWORD = "Ramovi07"
        private val KEYSTORE = "/Users/ismail/Library/Ethereum/keystore/"
        private val MAIN_WALLET_FILE = KEYSTORE + "UTC--2018-04-22T09-58-09.850325000Z--e12fb268f1ab83c738e48191d1d417d3e907e91e"
    }
}