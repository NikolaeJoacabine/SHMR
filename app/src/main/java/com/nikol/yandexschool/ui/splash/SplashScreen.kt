package com.nikol.yandexschool.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nikol.data.sync.SyncOrchestrator
import com.nikol.yandexschool.R
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

@Composable
internal fun SplashScreen(
    viewModel: SplashScreenViewModel,
    onGoToScreenAfterSplash: () -> Unit
) {
    val dateLoad = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.syncAllOrdered {
            dateLoad.value = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.file))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying && dateLoad.value) {
            onGoToScreenAfterSplash()
        }
    }
}

class SplashScreenViewModel(
    private val orchestrator: SyncOrchestrator
) : ViewModel() {

    fun syncAllOrdered(onComplete: () -> Unit) {
        viewModelScope.launch {
            orchestrator.syncAll()
            onComplete()
        }
    }

    class Factory @AssistedInject constructor(
        private val orchestrator: SyncOrchestrator
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashScreenViewModel(orchestrator) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(): SplashScreenViewModel.Factory
        }
    }
}