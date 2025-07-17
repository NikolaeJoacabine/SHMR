package com.nikol.data.util

fun String?.sanitizeComment(): String? {
    return this?.takeIf { it.isNotBlank() }
}