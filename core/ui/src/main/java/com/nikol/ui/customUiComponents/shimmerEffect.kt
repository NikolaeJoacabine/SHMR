package com.nikol.ui.customUiComponents


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize


fun Modifier.shimmerEffect(
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.4f),
        Color.LightGray.copy(0.6f)
    ),
    shimmerDuration: Int = 1000
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val translateX by transition.animateFloat(
        initialValue = -size.width.toFloat(),
        targetValue = size.width.toFloat() * 2,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = shimmerDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateX, 0f),
        end = Offset(translateX + size.width.toFloat(), 0f)
    )

    this
        .onGloballyPositioned { size = it.size }
        .background(brush)
}
