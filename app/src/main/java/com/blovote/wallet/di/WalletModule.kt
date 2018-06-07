package com.blovote.wallet.di

import com.blovote.account.data.AccountStorage
import com.blovote.wallet.presentation.WalletPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WalletModule {

    @Provides
    @Singleton
    fun provideWalletPresenter(accountStorage: AccountStorage) : WalletPresenter {
        return WalletPresenter(accountStorage)
    }
}