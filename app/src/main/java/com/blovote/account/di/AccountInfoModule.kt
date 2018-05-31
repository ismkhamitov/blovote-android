package com.blovote.account.di

import android.content.Context
import com.blovote.account.data.AccountStorage
import com.blovote.account.data.MockAccountStorage
import com.blovote.account.domain.AccountInteractor
import com.blovote.account.domain.AccountInteractorImpl
import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import javax.inject.Singleton

@Module
class AccountInfoModule {

    @Provides
    @Singleton
    fun provideAccountStorage(context: Context) : AccountStorage {
        //TODO: return AccountStorageImpl(context)
        return MockAccountStorage(context)
    }

    @Provides
    @Singleton
    fun provideAccountInteractor(web3j: Web3j, accountStorage: AccountStorage) : AccountInteractor {
        return AccountInteractorImpl(accountStorage, web3j)
    }

}