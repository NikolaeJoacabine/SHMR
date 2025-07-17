package com.nikol.data.articles.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.ArticlesState
import jakarta.inject.Inject
import retrofit2.HttpException

/**
 * Реализация [RemoteArticlesRepository], которая загружает статьи с удалённого API.
 *
 * Использует [financeAPI] для сетевых запросов и [networkStatusProvider] для проверки подключения к сети.
 */
class RemoteArticlesRepositoryImpl @Inject constructor(
    private val financeAPI: FinanceAPI,
    private val networkStatusProvider: NetworkStatusProvider
) : RemoteArticlesRepository {

    /**
     * Получает список статей с сервера.
     *
     * Если нет подключения к интернету, возвращает [ArticlesState.NetworkError].
     * В случае успешного ответа возвращает [ArticlesState.Success] с данными.
     * В случае ошибки возвращает [ArticlesState.Error] с сообщением об ошибке.
     */
    override suspend fun getArticles(): ArticlesState {

        if (!networkStatusProvider.isConnected()) {
            return ArticlesState.NetworkError
        }

        return runCatching { retryOnServerError { financeAPI.getArticles() } }
            .mapCatching { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    ArticlesState.Success(body.map { it.toDomain() })
                } else {
                    ArticlesState.Error("Ошибка загрузки статей")
                }
            }
            .getOrElse { e ->
                when (e) {
                    is HttpException -> ArticlesState.Error("error")
                    else -> ArticlesState.Error("error")
                }
            }
    }
}
