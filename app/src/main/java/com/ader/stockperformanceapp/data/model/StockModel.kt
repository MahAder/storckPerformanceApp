package com.ader.stockperformanceapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class StockModel(
    @SerializedName("symbol")val symbol: String,
    @SerializedName("timestamps")val timestamps: List<Long>,
    @SerializedName("opens")val opens: List<BigDecimal>,
    @SerializedName("closures")val closures: List<BigDecimal>,
    @SerializedName("highs")val highs: List<BigDecimal>,
    @SerializedName("lows")val lows: List<BigDecimal>,
    @SerializedName("volumes")val volumes: List<Int>
)