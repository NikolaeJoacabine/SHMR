package com.nikol.data.util.timeProvider

import com.nikol.domain.common.TimeProvider
import java.time.LocalDateTime

class DefaultTimeProvider : TimeProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}
