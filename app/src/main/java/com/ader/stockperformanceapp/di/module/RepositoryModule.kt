package com.ader.stockperformanceapp.di.module

import android.content.Context
import com.ader.stockperformanceapp.data.repository.IStockRepository
import com.ader.stockperformanceapp.data.repository.StockAssetRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideStockRepository(context: Context): IStockRepository{
        return StockAssetRepository(context)
    }
}