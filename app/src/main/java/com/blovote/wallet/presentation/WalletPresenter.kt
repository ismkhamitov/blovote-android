package com.blovote.wallet.presentation

import android.content.ContentResolver
import android.net.Uri
import com.blovote.account.data.AccountStorage
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File

class WalletPresenter(private val accountStorage: AccountStorage) {

    private var selectedFile : Uri? = null

    fun setSelectedFile(uri: Uri) {
        selectedFile = uri
    }

    fun createWallet(password: String) : Completable {
        return accountStorage.createNewWallet(password).toCompletable()
                .subscribeOn(Schedulers.computation())
    }

    fun saveExistingWallet(contentResolver: ContentResolver, password: String) : Single<Boolean> {
        if (selectedFile == null) {
            throw IllegalStateException()
        }

        val istream = contentResolver.openInputStream(selectedFile)
        return accountStorage.updateWallet(password, istream)
                .subscribeOn(Schedulers.computation())
                .doFinally {
                    istream.close()
                }
    }


}