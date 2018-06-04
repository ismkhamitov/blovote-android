package com.blovote.surveys.di

import android.content.Context
import com.blovote.account.data.AccountStorage
import com.blovote.contracts.di.NAMED_GOD_ADDRESS
import com.blovote.surveys.data.*
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.domain.SurveysInteractorImpl
import com.blovote.surveys.domain.SurveysRepository
import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import javax.inject.Named
import javax.inject.Singleton

const val DB_ALL_SURVEYS = "existing_surveys"

@Module
class SurveysModule {

    @Provides
    @Singleton
    fun provideSurveysDatabase(context : Context) : SurveysDatabase {
        return SurveysDatabase.getInstance(context, DB_ALL_SURVEYS)
    }

    @Provides
    @Singleton
    fun provideSureysStorage(database: SurveysDatabase) : SurveysStorage {
        return SurveysStorageImpl(database)
    }

    @Provides
    @Singleton
    fun provideSurveysRepository(storage: SurveysStorage, web3j: Web3j,
                                 @Named(NAMED_GOD_ADDRESS) godAddress : String,
                                 accountStorage: AccountStorage) : SurveysRepository {
        return SurveysRepositoryImpl(storage, web3j, godAddress, accountStorage)
    }

    @Provides
    @Singleton
    fun provideSurveysInteractor(repository: SurveysRepository, accountStorage: AccountStorage) : SurveysInteractor {
        return SurveysInteractorImpl(repository, accountStorage)
    }
}