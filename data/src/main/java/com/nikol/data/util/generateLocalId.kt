package com.nikol.data.util

fun generateLocalId(): Int {
    return (-System.currentTimeMillis()).toInt()
}
