package com.blovote.contracts.di

import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import javax.inject.Named
import javax.inject.Singleton

const val GOD_ADDRESS = "0x06914bd62654800d879acf940f23728132185830"
const val NAMED_GOD_ADDRESS = "god_address"

@Module
class ContractsModule {

    @Provides
    @Singleton
    @Named(NAMED_GOD_ADDRESS)
    fun provideGodAddress(web3j: Web3j) : String {
        return GOD_ADDRESS
    }

}