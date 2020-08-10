package com.ader.stockperformanceapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.ader.stockperformanceapp.R
import com.ader.stockperformanceapp.presentation.model.ChartListModel
import kotlinx.android.synthetic.main.item_chart.view.*

class ChartListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = ArrayList<ChartListModel>()
    var onChartCheckedChangeListener: OnChartCheckedChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chart, parent,
            false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChartViewHolder).bind(position)
    }

    fun insertData(data: List<ChartListModel>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ChartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val chartListModel = data[position]
            with(itemView){
                chartColorIv.setBackgroundColor(chartListModel.chartColor)
                chartNameTv.text = chartListModel.chartName
                chartEnabledCb.isChecked = chartListModel.enabled

                chartEnabledCb.setOnCheckedChangeListener { _, p1 ->
                    onChartCheckedChangeListener?.onChartCheckedChange(position, p1)
                }
            }
        }
    }

    interface OnChartCheckedChangeListener{
        fun onChartCheckedChange(index: Int, checked: Boolean)
    }
}