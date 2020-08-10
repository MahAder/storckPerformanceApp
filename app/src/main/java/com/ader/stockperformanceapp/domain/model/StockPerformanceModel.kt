package com.ader.stockperformanceapp.domain.model

import java.math.BigDecimal

data class StockPerformanceModel(
    val name: String,
    val percentagePerformance: List<BigDecimal>,
    val timestamps: List<Long>
)