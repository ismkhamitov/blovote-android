package com.blovote.contracts.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

const val GOD_ADDRESS = "0xe41c737ec7989aaab8bcc8ca2b7a527017fe5488"
const val NAMED_GOD_ADDRESS = "god_address"

@Module
class ContractsModule {

    @Provides
    @Singleton
    @Named(NAMED_GOD_ADDRESS)
    fun provideGodAddress() : String {
        return GOD_ADDRESS
    }

}