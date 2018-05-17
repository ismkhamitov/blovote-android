package com.blovote.account.di

import android.content.Context
import com.blovote.account.data.AccountStorage
import com.blovote.account.data.MockAccountStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountInfoModule {

    @Provides
    @Singleton
    fun provideAccountStorage(context: Context) : AccountStorage {
        //TODO: return AccountStorageImpl(context)
        return MockAccountStorage(context)
    }

}