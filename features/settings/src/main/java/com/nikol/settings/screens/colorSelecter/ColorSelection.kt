package com.nikol.settings.screens.colorSelecter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nikol.ui.theme.color.RosePrimaryDark
import com.nikol.ui.theme.color.greenPrimaryDark
import com.nikol.ui.theme.color.purplePrimaryDark
import com.nikol.ui.theme.color.violetPrimaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSelectionScreen(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    navigateBack: () -> Unit
) {
    val availableColors = listOf(
        greenPrimaryDark,
        purplePrimaryDark,
        RosePrimaryDark,
        violetPrimaryDark
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Выбор цвета",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                availableColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (color == selectedColor) 3.dp else 1.dp,
                                color = if (color == selectedColor) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .clickable { onColorSelected(color) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (color == selectedColor) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Выбранный цвет",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}