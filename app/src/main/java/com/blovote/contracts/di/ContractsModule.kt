package com.blovote.contracts.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

const val GOD_ADDRESS = "0xd86dc860758e7afd6812f4be002dd87513bae24b"
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