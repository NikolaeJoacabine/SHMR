package com.nikol.domain.common

import java.time.LocalDateTime

interface TimeProvider {
    fun now(): LocalDateTime
}