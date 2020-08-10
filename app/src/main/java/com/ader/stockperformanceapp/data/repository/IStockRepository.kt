package com.ader.stockperformanceapp.data.repository

import com.ader.stockperformanceapp.data.model.StockArrayModel

interface IStockRepository {
    suspend fun getMonthStocks(): StockArrayModel?
    suspend fun getWeekStocks(): StockArrayModel?
}