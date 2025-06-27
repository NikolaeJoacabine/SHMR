package com.nikol.yandexschool.features.articles.screnns.articles.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikol.domain.model.Articles

@Composable
fun ArticlesContentState(list: List<Articles>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(list, key = { it.id }) { article ->
            ArticleListItem(article)
        }
    }
}
