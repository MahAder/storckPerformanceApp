package com.ader.stockperformanceapp.di.module

import com.ader.stockperformanceapp.data.repository.IStockRepository
import com.ader.stockperformanceapp.domain.interactor.IStockPerformanceInteractor
import com.ader.stockperformanceapp.domain.interactor.StockPerformanceInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideStockInteractor(stockRepository: IStockRepository): IStockPerformanceInteractor {
        return StockPerformanceInteractor(stockRepository)
    }
}