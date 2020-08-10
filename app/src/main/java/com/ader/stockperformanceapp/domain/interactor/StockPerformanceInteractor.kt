package com.ader.stockperformanceapp.domain.interactor

import com.ader.stockperformanceapp.data.model.StockModel
import com.ader.stockperformanceapp.data.repository.IStockRepository
import com.ader.stockperformanceapp.domain.model.StockPerformanceModel
import java.math.BigDecimal
import java.math.RoundingMode

class StockPerformanceInteractor(private val stockRepository: IStockRepository): IStockPerformanceInteractor {
    override suspend fun getMonthStockPerformances(): List<StockPerformanceModel> {
        val list = ArrayList<StockPerformanceModel>()
        val stocks = stockRepository.getMonthStocks()
        stocks?.let {
            for(stock in it.stocks){
                list.add(getStockPerformance(stock))
            }
        }

        return list
    }

    override suspend fun getWeekStockPerformances(): List<StockPerformanceModel> {
        val list = ArrayList<StockPerformanceModel>()
        val stocks = stockRepository.getWeekStocks()
        stocks?.let {
            for(stock in it.stocks){
                list.add(getStockPerformance(stock))
            }
        }

        return list
    }

    private fun getStockPerformance(stockModel: StockModel): StockPerformanceModel {
        val startValue = getStartValueFromStock(stockModel)
        val performanceList = ArrayList<BigDecimal>()
        for (periodEndValue in stockModel.closures){
            performanceList.add(calculatePerformanceForOnePeriod(startValue, periodEndValue))
        }

        return StockPerformanceModel(stockModel.symbol, performanceList, stockModel.timestamps)
    }

    private fun calculatePerformanceForOnePeriod(startValue: BigDecimal, endValue: BigDecimal): BigDecimal{
        val bigDecimal100 = BigDecimal(100)
        val bigDecimal1 = BigDecimal(1)
        return (((endValue.divide(startValue, 5, RoundingMode.HALF_EVEN)) - bigDecimal1) * bigDecimal100)
    }

    private fun getStartValueFromStock(stockModel: StockModel): BigDecimal{
        return stockModel.opens[0]
    }
}