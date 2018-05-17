package com.blovote.account.data

import org.web3j.crypto.Credentials
import java.io.File

interface AccountStorage {

    fun isWalletExists() : Boolean

    fun createNewWallet(password: String): Credentials

    fun loadCredentials(): Credentials

    fun updateCredentials(password: String, walletFile: File): Boolean
}