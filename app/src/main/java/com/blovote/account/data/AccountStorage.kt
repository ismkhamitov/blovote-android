package com.blovote.account.data

import io.reactivex.Single
import org.web3j.crypto.Credentials
import java.io.InputStream

interface AccountStorage {

    fun isWalletExists() : Boolean

    fun createNewWallet(password: String): Single<Credentials>

    fun updateWallet(password: String, walletFileStream: InputStream): Single<Boolean>


    fun areCredentialsLoaded() : Boolean

    fun loadCredentials() : Single<Credentials>

    fun getCredentials(): Credentials
}