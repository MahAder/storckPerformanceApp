package com.ader.stockperformanceapp.di.module

import android.content.Context
import com.ader.stockperformanceapp.StockPerformanceApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: StockPerformanceApplication): Context {
        return application
    }
}