package com.ader.stockperformanceapp.presentation.model

import java.math.BigDecimal

data class StockPerformanceChartModel(
    val name: String,
    val performanceList: List<BigDecimal>,
    val timestamps: List<Long>,
    val color: Int,
    val size: Int,
    var enabled: Boolean
)