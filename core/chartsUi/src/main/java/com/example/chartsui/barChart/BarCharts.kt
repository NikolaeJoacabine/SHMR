package com.example.chartsui.barChart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikol.ui.theme.YandexSchoolTheme
import java.time.LocalDate
import kotlin.math.abs
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BarChart(
    list: List<Bar>,
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    barWidth: Dp = 24.dp,
    barSpacing: Dp = 20.dp,
    minVisibleBars: Int = 6,
    startOffset: Dp = 8.dp,
) {
    if (list.isEmpty()) return

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val maxCount = (list.maxOfOrNull { abs(it.count) } ?: 1).coerceAtLeast(1)

    val barWidthPx = with(density) { barWidth.toPx() }
    val barSpacingPx = with(density) { barSpacing.toPx() }
    val startOffsetPx = with(density) { startOffset.toPx() }
    val cornerRadius = with(density) { 6.dp.toPx() }
    val labelAreaHeight = with(density) { 36.dp.toPx() }

    val verticalOffset = with(density) { 6.dp.toPx() } // сдвиг вниз baseline и подписей

    val totalBars = max(list.size, minVisibleBars)
    val contentWidth = (barWidthPx + barSpacingPx) * totalBars

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(height + 36.dp)
                .drawBehind {
                    val chartHeight = size.height - labelAreaHeight - verticalOffset
                    val baselineY = chartHeight

                    val startX = size.width - contentWidth - startOffsetPx

                    list.forEachIndexed { index, bar ->
                        val x = startX + index * (barWidthPx + barSpacingPx)
                        val centerX = x + barWidthPx / 2
                        val barHeight = (abs(bar.count).toFloat() / maxCount) * chartHeight

                        drawRoundRect(
                            color = if (bar.isNegative) Color(0xFFE57373) else Color(0xFF81C784),
                            topLeft = Offset(x, baselineY - barHeight),
                            size = Size(barWidthPx, barHeight),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                        )

                        drawLine(
                            color = Color.Gray,
                            start = Offset(centerX, baselineY + verticalOffset),
                            end = Offset(centerX, baselineY + verticalOffset + 8.dp.toPx()),
                            strokeWidth = 2.dp.toPx()
                        )

                        val label = bar.x
                        val textStyle = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        val textLayoutResult = textMeasurer.measure(
                            AnnotatedString(label),
                            style = textStyle
                        )
                        val textX = centerX - textLayoutResult.size.width / 2
                        val textY = baselineY + verticalOffset + 10.dp.toPx()

                        drawText(
                            textMeasurer,
                            text = AnnotatedString(label),
                            topLeft = Offset(textX, textY),
                            style = textStyle
                        )
                    }

                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, baselineY + verticalOffset),
                        end = Offset(size.width, baselineY + verticalOffset),
                        strokeWidth = 2.dp.toPx()
                    )
                }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun fff() {
    YandexSchoolTheme {
        val bars = listOf(
            Bar(false, 50, LocalDate.now().minusDays(6)),
            Bar(true, 30, LocalDate.now().minusDays(5)),
            Bar(false, 80, LocalDate.now().minusDays(4)),
            Bar(false, 60, LocalDate.now().minusDays(3)),
            Bar(true, 40, LocalDate.now().minusDays(2)),
            Bar(true, 20, LocalDate.now().minusDays(1)),
            Bar(false, 90, LocalDate.now())
        )

        BarChart(list = bars)
    }
}