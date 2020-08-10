package com.ader.stockperformanceapp.domain.interactor

import com.ader.stockperformanceapp.domain.model.StockPerformanceModel

interface IStockPerformanceInteractor {
    suspend fun getMonthStockPerformances(): List<StockPerformanceModel>
    suspend fun getWeekStockPerformances(): List<StockPerformanceModel>
}