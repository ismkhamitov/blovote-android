package com.blovote.app

import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Singleton

@Module
class AppModule(context : Context) {

    private var mContext : Context = context

    @Provides
    @Singleton
    fun provideAppContext() : Context {
        return mContext
    }

    @Provides
    @Singleton
    fun provideBackgroundExecutor() : ExecutorService {
        return Executors.newFixedThreadPool(4)
    }


}