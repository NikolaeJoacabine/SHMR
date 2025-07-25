package com.example.chartsui.barChart

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Bar(
    val isNegative: Boolean,
    val count: Int,
    val date: LocalDate
) {
    val x: String @RequiresApi(Build.VERSION_CODES.O)
    get() = date.format(DateTimeFormatter.ofPattern("dd.MM"))
}