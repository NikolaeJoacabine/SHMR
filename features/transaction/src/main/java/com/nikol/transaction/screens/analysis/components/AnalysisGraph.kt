package com.nikol.transaction.screens.analysis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chartsui.pieChart.AnimatedRingPieChart
import com.example.chartsui.pieChart.PieChartEntry

@Composable
internal fun AnalysisGraph(
    list: List<PieChartEntry>,
    modifier: Modifier = Modifier,
    legend: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AnimatedRingPieChart(entries = list, animationDuration = 500)
            legend()
        }
    }
}
