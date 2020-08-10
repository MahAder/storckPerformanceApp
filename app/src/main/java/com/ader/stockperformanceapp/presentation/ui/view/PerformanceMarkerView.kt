package com.ader.stockperformanceapp.presentation.ui.view

import android.content.Context
import com.ader.stockperformanceapp.Utils.convertToString
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.performance_marker_view.view.*
import java.util.*


class PerformanceMarkerView(context: Context, layoutResource: Int) : MarkerView(context,
    layoutResource
) {
    private var mOffset: MPPointF? = null

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        e?.let {
            val date = Date(e.x.toLong())
            val dateString = date.convertToString()

            contentTv.text = dateString
        }
    }

    override fun getOffset(): MPPointF? {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
        return mOffset
    }
}