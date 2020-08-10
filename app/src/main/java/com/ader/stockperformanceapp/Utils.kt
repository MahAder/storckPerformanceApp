package com.ader.stockperformanceapp

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    private val random = Random()

    fun Date.convertToString(): String{
        return dateFormatter.format(this)
    }

    fun convertFromUnixTime(timestamp: Long): Long {
        return timestamp * 1000
    }

    fun generateRandomColor(): Int{
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}