package com.blovote.account.data

import io.reactivex.Single
import org.web3j.crypto.Credentials
import java.io.File
import java.math.BigInteger

interface AccountStorage {

    fun isWalletExists() : Boolean

    fun createNewWallet(password: String): Credentials

    fun loadCredentials(): Credentials

    fun updateCredentials(password: String, walletFile: File): Boolean
}