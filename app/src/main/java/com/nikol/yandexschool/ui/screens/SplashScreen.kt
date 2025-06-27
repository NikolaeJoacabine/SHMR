package com.nikol.yandexschool.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit) {

    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        hasNavigated = true
        onNavigate()
    }

    if (!hasNavigated) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Asset("file.json"),
                autoplay = true,
                loop = false,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier.size(500.dp)
            )
        }
    }
}
