package com.ader.stockperformanceapp.presentation.viewmodel

import android.graphics.Color
import androidx.lifecycle.*
import com.ader.stockperformanceapp.Utils.convertFromUnixTime
import com.ader.stockperformanceapp.Utils.generateRandomColor
import com.ader.stockperformanceapp.domain.interactor.IStockPerformanceInteractor
import com.ader.stockperformanceapp.domain.model.StockPerformanceModel
import com.ader.stockperformanceapp.presentation.model.ChartListModel
import com.ader.stockperformanceapp.presentation.model.StockPerformanceChartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivityViewModel(private val stockPerformanceInteractor: IStockPerformanceInteractor) :
    ViewModel() {

    val stockPerformanceMonthLiveData: MediatorLiveData<List<StockPerformanceChartModel>> =
        MediatorLiveData()
    val loaderMonthWeekLiveData = MutableLiveData<Boolean>()
    val chartListLiveData = MutableLiveData<List<ChartListModel>>()

    private val enabledChartHashMap = HashMap<String, Boolean>()
    private val chartColors = HashMap<String, Int>()

    init {
        loaderMonthWeekLiveData.postValue(true)
        stockPerformanceMonthLiveData.addSource(loaderMonthWeekLiveData) {
            loadStockPerformances(it)
        }
    }

    private fun loadStockPerformances(forWeek: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val performanceList = if (forWeek) stockPerformanceInteractor.getWeekStockPerformances()
            else stockPerformanceInteractor.getMonthStockPerformances()
            val mappedPerformanceList = performanceList.map {
                val map = mapStockPerformanceModelToChartModel(it)
                return@map map
            }
            stockPerformanceMonthLiveData.postValue(mappedPerformanceList)
            initChartListData(mappedPerformanceList)
        }
    }

    private fun initChartListData(stockList: List<StockPerformanceChartModel>) {
        val chartModelList = ArrayList<ChartListModel>()
        for (stock in stockList) {
            chartModelList.add(
                ChartListModel(
                    stock.name,
                    stock.color,
                    stock.enabled
                )
            )
        }
        chartListLiveData.postValue(chartModelList)
    }

    private fun mapStockPerformanceModelToChartModel(stockPerformanceModel: StockPerformanceModel): StockPerformanceChartModel {
        val color = if(chartColors.containsKey(stockPerformanceModel.name)) {
             chartColors[stockPerformanceModel.name]!!
        } else {
            val generatedColor = generateRandomColor()
            chartColors[stockPerformanceModel.name] = generatedColor
            generatedColor
        }

        return StockPerformanceChartModel(
            stockPerformanceModel.name,
            stockPerformanceModel.percentagePerformance,
            convertTimestamps(stockPerformanceModel.timestamps),
            color,
            stockPerformanceModel.percentagePerformance.size,
            enabledChartHashMap[stockPerformanceModel.name] ?: true
        )
    }

    fun changeChartVisibility(index: Int, visible: Boolean) {
        stockPerformanceMonthLiveData.value?.let {
            enabledChartHashMap[it[index].name] = visible
            it[index].enabled = visible
            stockPerformanceMonthLiveData.postValue(it)
        }
    }

    private fun convertTimestamps(timestamps: List<Long>): List<Long> {
        val list = ArrayList<Long>()
        for (timestamp in timestamps) {
            list.add(convertFromUnixTime(timestamp))
        }

        return list
    }
}