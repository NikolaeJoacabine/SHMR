package com.example.chartsui.pieChart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedRingPieChart(
    entries: List<PieChartEntry>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 40f,
    animationDuration: Int = 1000,
) {
    val total = remember(entries) {
        entries.sumOf { it.value.toDouble() }.toFloat()
    }

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(entries) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
        )
    }


    Canvas(
        modifier = modifier
            .size(200.dp)
            .padding((strokeWidth / 2).dp)
    ) {
        var startAngle = -90f
        val currentSweep = 360f * animatedProgress.value

        entries.forEach { entry ->
            val sweepAngle = 360f * (entry.value / total)
            val drawAngle = (currentSweep - (startAngle + 90f)).coerceAtMost(sweepAngle)

            if (drawAngle > 0f) {
                drawArc(
                    color = entry.color,
                    startAngle = startAngle,
                    sweepAngle = drawAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
            }

            startAngle += sweepAngle
        }
    }
}
