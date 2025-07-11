package com.nikol.ui.customUiComponents

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikol.ui.R

@Composable
fun ChevronRight() {
    Icon(
        painter = painterResource(R.drawable.chevron_right),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
        modifier = Modifier.size(24.dp)
    )
}
