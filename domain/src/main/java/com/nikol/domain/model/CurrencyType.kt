package com.nikol.domain.model


enum class CurrencyType(
    val type: String,
    val str: String,
    val description: String
) {
    RUB(type = "RUB", str = "₽", description = "Российский рубль ₽"),
    USD(type = "USD", str = "$", description = "Американский доллар $"),
    EUR(type = "EUR", str = "€", description = "Евро")
}
