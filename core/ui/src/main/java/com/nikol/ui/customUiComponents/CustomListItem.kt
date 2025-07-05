package com.nikol.ui.customUiComponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikol.ui.theme.YandexSchoolTheme

/**
 * Базовый элемент списка с поддержкой слотов: ведущий элемент (lead), основной контент (content),
 * и дополнительный контент справа (trailing).
 *
 * Компонент предназначен для универсального отображения строк в списках, карточках и т.д.
 * Вся строка выровнена по вертикали по центру и имеет внутренние отступы.
 *
 * ## Пример использования:
 * ```
 * CustomListItem(
 *     lead = { Icon(Icons.Default.Person) },
 *     content = { Text("Пользователь") },
 *     trailing = {
 *         Text("Детали")
 *         IconButton(onClick = { /* ... */ }) {
 *             Icon(Icons.Default.Edit)
 *         }
 *     }
 * )
 * ```
 *
 * @param modifier Модификатор для настройки внешнего вида и поведения элемента.
 * @param lead Компонент, отображаемый слева (например, иконка или изображение). Опционально.
 * @param content Основной контент элемента (например, текст заголовка и подзаголовка).
 * @param trailing Дополнительный контент справа (например, текст, кнопки, Switch). Опционально.
 * Может содержать интерактивные элементы (например, `IconButton` или `Switch`
 * или любой компонент имеющий модификатор `clickable`).
 */
@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    lead: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    trailing: @Composable RowScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левый элемент (lead)
            if (lead != null) {
                Box(modifier = Modifier.padding(end = 16.dp)) {
                    lead()
                }
            }

            // Основной контент
            Box(modifier = Modifier.weight(1f)) {
                content()
            }

            // Правый контент (trailing)
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                trailing()
            }
        }
        HorizontalDivider()
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_5",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    apiLevel = 35
)
@Composable
private fun PreviewCustomListItem() {
    YandexSchoolTheme {
        CustomListItem(
            lead = { Icon(Icons.Default.Home, contentDescription = null) },
            content = { Text("Аренда квартиры") },
            trailing = {
                Text("100 000")
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Create, contentDescription = null)
                }
            }
        )
    }
}
