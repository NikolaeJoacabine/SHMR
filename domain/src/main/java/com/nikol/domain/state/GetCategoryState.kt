package com.nikol.domain.state

import com.nikol.domain.model.Category

sealed class GetCategoryState {
    data object Error : GetCategoryState()
    data class Success(val category: List<Category>) : GetCategoryState()
}
