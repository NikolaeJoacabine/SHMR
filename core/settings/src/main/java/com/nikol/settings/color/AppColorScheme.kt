package com.nikol.settings.color

enum class AppColorScheme {
    Green,
    Purple,
    Rose,
    Violet;

    companion object {
        fun fromName(name: String?): AppColorScheme =
            entries.find { it.name == name } ?: Green
    }
}
