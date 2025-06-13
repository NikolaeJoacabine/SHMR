package com.nikol.yandexschool.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikol.yandexschool.R
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem

@Composable
fun SettingsScreen() {
    val state = rememberScrollState()
    val switch = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .padding(16.dp),
            content = { Text("Тёмная тема") },
            trailing = {
                Switch(
                    checked = switch.value,
                    onCheckedChange = { switch.value = !switch.value })
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Основной цвет") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Звуки") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Хаптики") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Код пароль") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Синхронизация") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("Язык") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
        CustomListItem(
            modifier = Modifier
                .height(55.dp)
                .clickable {}
                .padding(16.dp),
            content = { Text("О программе") },
            trailing = {
                Icon(painterResource(R.drawable.arrow_right), contentDescription = null)
            }
        )
    }
}