package com.nikol.articles.screens.articles.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.domain.model.Articles
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon

@Composable
fun ArticleListItem(article: Articles) {
    CustomListItem(
        modifier = Modifier
            .height(68.dp)
            .padding(horizontal = 16.dp),
        lead = { EmojiIcon(article.emoji) },
        content = { Text(text = article.name) }
    )
}
