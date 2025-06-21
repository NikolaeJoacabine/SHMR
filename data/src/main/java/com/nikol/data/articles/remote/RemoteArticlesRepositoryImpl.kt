package com.nikol.data.articles.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.ArticlesState
import retrofit2.HttpException

class RemoteArticlesRepositoryImpl(private val financeAPI: FinanceAPI) : RemoteArticlesRepository {

    override suspend fun getArticles(): ArticlesState {
        return runCatching { retryOnServerError { financeAPI.getArticles() } }
            .mapCatching { response ->
                if (response.isSuccessful && response.body() != null) {
                    ArticlesState.Success(response.body()!!.map { it.toDomain() })
                } else {
                    ArticlesState.Error("error")
                }
            }.getOrElse { e ->
                when (e) {
                    is HttpException -> ArticlesState.Error("error")
                    else -> ArticlesState.Error("error")
                }
            }
    }
}