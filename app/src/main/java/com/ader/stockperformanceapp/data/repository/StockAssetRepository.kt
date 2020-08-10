package com.ader.stockperformanceapp.data.repository

import android.content.Context
import com.ader.stockperformanceapp.data.model.StockArrayModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.InputStream

class StockAssetRepository(private val context: Context): IStockRepository{
    override suspend fun getMonthStocks(): StockArrayModel? {
        val jsonString = readJsonFileFromAsset(MONTH_STOCK_FILE_NAME)
        jsonString?.let {
            return convertJsonToStockArrayModel(it)
        }

        return null
    }

    override suspend fun getWeekStocks(): StockArrayModel? {
        val jsonString = readJsonFileFromAsset(WEEK_STOCK_FILE_NAME)
        jsonString?.let {
            return convertJsonToStockArrayModel(it)
        }

        return null
    }

    private fun readJsonFileFromAsset(name: String): String? {
        val json: String?
        try {
            val  inputStream: InputStream = context.assets.open(name)
            val jsonObject = JSONObject(inputStream.bufferedReader().use{it.readText()})
            json = jsonObject.getJSONObject(STOCK_CONTENT_KEY).toString()
        } catch (ex: Exception) {
            return null
        }
        return json
    }

    private fun convertJsonToStockArrayModel(jsonString: String): StockArrayModel? {
        return try {
            Gson().fromJson(jsonString, StockArrayModel::class.java)
        }catch (exception: JsonSyntaxException){
            null
        }
    }

    companion object {
        private const val MONTH_STOCK_FILE_NAME = "responseQuotesMonth.json"
        private const val WEEK_STOCK_FILE_NAME = "responseQuotesWeek.json"
        private const val STOCK_CONTENT_KEY = "content"
    }
}