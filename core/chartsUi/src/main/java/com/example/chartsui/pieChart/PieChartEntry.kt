package com.example.chartsui.pieChart

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PieChartEntry(
    val value: Float,
    val color: Color
)