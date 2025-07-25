package com.nikol.transaction.screens.analysis.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.chartsui.pieChart.PieChartEntry
import com.example.chartsui.pieChart.PleasantColors.pleasantColors
import com.nikol.data.util.formater.formatAmount
import com.nikol.domain.model.Category
import com.nikol.domain.model.CurrencyType
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon
import com.nikol.ui.formatter.formatPercent
import kotlin.random.Random

@Composable
fun AnalysisList(
    list: List<Category>,
    currencyType: CurrencyType
) {

    val entries = remember(list) {
        list.mapIndexed { index, category ->
            PieChartEntry(
                value = category.percent.toFloat(),
                color =
                    pleasantColors.getOrElse(index) {
                        Color(
                            red = Random.nextFloat(),
                            green = Random.nextFloat(),
                            blue = Random.nextFloat(),
                            alpha = 1f
                        )
                    }
            )
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            AnalysisGraph(list = entries) {
                list.forEachIndexed { index, category ->
                    CustomListItem(
                        modifier = Modifier
                            .height(70.dp)
                            .padding(horizontal = 16.dp),
                        lead = {
                            EmojiIcon(category.emoji ?: "")
                        },
                        content = {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailing = {
                            Column(
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = category.percent.formatPercent(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = formatAmount(category.amount.toString()) + " ${currencyType.str}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Spacer(
                                Modifier
                                    .size(24.dp)
                                    .drawBehind {
                                        drawRoundRect(
                                            pleasantColors[index],
                                            cornerRadius = CornerRadius(20f)
                                        )
                                    }
                            )
                        }
                    )
                }
            }
        }
    }
}
