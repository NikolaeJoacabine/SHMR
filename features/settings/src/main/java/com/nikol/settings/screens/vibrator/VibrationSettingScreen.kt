package com.nikol.settings.screens.vibrator

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.settings.screens.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun VibrationSettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val vibrationEnabled by viewModel.vibrationEnabled.collectAsStateWithLifecycle()
    val vibrationEffect by viewModel.vibrationEffect.collectAsStateWithLifecycle()

    val vibrationOptions = listOf(
        VibrationEffect.EFFECT_CLICK to "Click",
        VibrationEffect.EFFECT_DOUBLE_CLICK to "Double Click",
        VibrationEffect.EFFECT_TICK to "Tick",
        VibrationEffect.EFFECT_HEAVY_CLICK to "Heavy Click"
    )

    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Вибрация",
                        style = MaterialTheme.typography.titleLarge,
                        color = onPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Включить вибрацию", style = MaterialTheme.typography.titleMedium)
            Switch(
                checked = vibrationEnabled,
                onCheckedChange = { viewModel.setVibrationEnabled(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Выберите эффект вибрации", style = MaterialTheme.typography.titleMedium)
            vibrationOptions.forEach { (effectValue, label) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (vibrationEnabled) 1f else 0.4f)
                        .selectable(
                            enabled = vibrationEnabled,
                            selected = (vibrationEffect == effectValue),
                            onClick = {
                                if (vibrationEnabled) {
                                    viewModel.setVibrationEffect(effectValue)
                                }
                            }
                        )
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = (vibrationEffect == effectValue),
                        onClick = {
                            if (vibrationEnabled) {
                                viewModel.setVibrationEffect(effectValue)
                            }
                        },
                        enabled = vibrationEnabled
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label)
                }
            }
        }
    }
}
