package com.ader.stockperformanceapp.presentation.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ader.stockperformanceapp.R
import com.ader.stockperformanceapp.Utils.convertToString
import com.ader.stockperformanceapp.databinding.ActivityMainBinding
import com.ader.stockperformanceapp.presentation.model.ChartListModel
import com.ader.stockperformanceapp.presentation.model.StockPerformanceChartModel
import com.ader.stockperformanceapp.presentation.ui.adapter.ChartListAdapter
import com.ader.stockperformanceapp.presentation.ui.view.PerformanceMarkerView
import com.ader.stockperformanceapp.presentation.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity: DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: MainActivityViewModel

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initChart()
        initChartList()
    }

    private fun initChart(){
        performanceChart.setScaleEnabled(true)
        performanceChart.xAxis.labelRotationAngle = 30f
        performanceChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return Date(value.toLong()).convertToString()
            }
        }
        performanceChart.setScaleMinima(10f, 0.5f)
        performanceChart.marker = PerformanceMarkerView(this, R.layout.performance_marker_view)
    }

    private fun initChartList(){
        val adapter = ChartListAdapter()
        adapter.onChartCheckedChangeListener = object : ChartListAdapter.OnChartCheckedChangeListener {
            override fun onChartCheckedChange(index: Int, checked: Boolean) {
                viewModel.changeChartVisibility(index, checked)
            }
        }
        chartList.adapter = adapter
        chartList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    object MainActivityAdapter{
        @JvmStatic
        @BindingAdapter("chartData")
        fun chartData(lineChart: LineChart, stockPerformanceList: List<StockPerformanceChartModel>?){
            if(stockPerformanceList == null) return
            val lineData = LineData()
            for(stockPerformanceChartModel in stockPerformanceList) {
                if (stockPerformanceChartModel.enabled) {
                    val entryList = ArrayList<Entry>()
                    for (i in 0 until stockPerformanceChartModel.size) {
                        entryList.add(
                            Entry(
                                stockPerformanceChartModel.timestamps[i].toFloat(),
                                stockPerformanceChartModel.performanceList[i].toFloat()
                            )
                        )
                    }

                    val lineDataSet = LineDataSet(entryList, stockPerformanceChartModel.name)
                    lineDataSet.color = stockPerformanceChartModel.color

                    lineData.addDataSet(lineDataSet)
                }
            }
            lineChart.data = lineData
            lineChart.invalidate()
            lineChart.notifyDataSetChanged()
            lineChart.moveViewToX(lineData.xMax)
        }

        @JvmStatic
        @BindingAdapter("chartList")
        fun setChartListData(recyclerView: RecyclerView, chartList : List<ChartListModel>?){
            if (chartList == null) return
            (recyclerView.adapter as ChartListAdapter).insertData(chartList)
        }
    }
}