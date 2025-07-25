package com.nikol.settings.lastUpdate

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getLastUpdateDate(context: Context, locale: Locale = Locale.getDefault()): String {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val lastUpdateTime = packageInfo.lastUpdateTime
    val formatter = SimpleDateFormat("dd MMM yyyy", locale)
    return formatter.format(Date(lastUpdateTime))
}
