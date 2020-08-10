package com.ader.stockperformanceapp.data.model

import com.google.gson.annotations.SerializedName

data class StockArrayModel(
    @SerializedName("quoteSymbols")val stocks: List<StockModel>
)