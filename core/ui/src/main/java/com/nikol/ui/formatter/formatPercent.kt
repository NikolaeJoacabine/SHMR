package com.nikol.ui.formatter

import java.util.Locale

fun Double.formatPercent(): String = String.format(Locale.getDefault(), "%.2f%%", this)
