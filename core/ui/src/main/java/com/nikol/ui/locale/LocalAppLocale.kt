package com.nikol.ui.locale

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

val LocalAppLocale = staticCompositionLocalOf { Locale.getDefault() }

fun Context.applyLocale(locale: Locale): Context {
    val config = resources.configuration
    config.setLocale(locale)
    return createConfigurationContext(config)
}
