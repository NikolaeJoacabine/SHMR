package com.nikol.settings.screens.pincode

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.settings.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeSelectScreen(
    onPinEntered: (String) -> Unit,
    onBackClick: () -> Unit,
    onClickClear: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var enteredPin by remember { mutableStateOf("") }
    var firstPin by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }
    var animateReset by remember { mutableStateOf(false) }

    LaunchedEffect(animateReset) {
        if (animateReset) {
            delay(300)
            while (enteredPin.isNotEmpty()) {
                delay(80)
                enteredPin = enteredPin.dropLast(1)
            }
            isError = false
            animateReset = false
        }
    }

    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(isError) {
        if (isError) {
            shakeOffset.snapTo(0f)
            repeat(3) {
                shakeOffset.animateTo(-10f, animationSpec = tween(50))
                shakeOffset.animateTo(10f, animationSpec = tween(50))
            }
            shakeOffset.animateTo(0f, animationSpec = tween(50))
        }
    }

    fun addDigit(digit: String) {
        if (enteredPin.length < 4) {
            enteredPin += digit
            if (enteredPin.length == 4) {
                if (step == 1) {
                    firstPin = enteredPin
                    enteredPin = ""
                    step = 2
                } else {
                    if (enteredPin == firstPin) {
                        onPinEntered(enteredPin)
                    } else {
                        isError = true
                        animateReset = true
                    }
                }
            }
        }
    }

    fun removeDigit() {
        if (enteredPin.isNotEmpty()) {
            enteredPin = enteredPin.dropLast(1)
        }
    }

    @Composable
    fun KeyButton(digit: String, modifier: Modifier = Modifier) {
        ElevatedButton(
            onClick = { addDigit(digit) },
            modifier = modifier.size(60.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text(
                text = digit,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

    @Composable
    fun BackspaceButton(modifier: Modifier = Modifier) {
        IconButton(
            onClick = { removeDigit() },
            modifier = modifier.size(72.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.backspace_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                contentDescription = "Удалить",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.pin_code),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = if (step == 1)
                    stringResource(R.string.come_up_with_a_new_PIN_code)
                else
                    stringResource(R.string.repeat_the_PIN_code),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .offset(x = shakeOffset.value.dp)
            ) {
                repeat(4) { index ->
                    val isFilled = index < enteredPin.length
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(
                                color = when {
                                    isError -> MaterialTheme.colorScheme.error
                                    isFilled -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                            .border(
                                1.dp,
                                if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                                CircleShape
                            )
                    )
                }
            }


            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf(
                    listOf("1", "2", "3"),
                    listOf("4", "5", "6"),
                    listOf("7", "8", "9")
                ).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        row.forEach { digit ->
                            KeyButton(digit, modifier = Modifier.weight(1f))
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    KeyButton("0", modifier = Modifier.weight(1f))
                    BackspaceButton(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button( onClick = {
                 onClickClear()
            }) {
                Text(stringResource(R.string.clear_passcode))
            }
        }
    }
}
