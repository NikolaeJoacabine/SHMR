package com.nikol.data.network

import com.nikol.data.model.AccountDTO
import com.nikol.data.model.AccountEditDTO
import com.nikol.data.model.AccountUpdateRequestDTO
import com.nikol.data.model.ArticlesDTO
import com.nikol.data.model.CreateTransactionDTO
import com.nikol.data.model.TransactionDTO
import com.nikol.domain.model.AccountUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API интерфейс для взаимодействия с финансовым сервером.
 * Предоставляет методы для получения транзакций, категорий (статей) и аккаунтов.
 */
interface FinanceAPI {

    /**
     * Получить список транзакций за указанный период для конкретного аккаунта.
     *
     * @param accountId Идентификатор аккаунта.
     * @param startDate Дата начала периода выборки транзакций в формате ISO (yyyy-MM-dd).
     * @param endDate Дата окончания периода выборки транзакций в формате ISO (yyyy-MM-dd).
     * @return [Response] с списком транзакций ([TransactionDTO]) или ошибкой.
     */
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<TransactionDTO>>

    /**
     * Получить список категорий (статей) для транзакций.
     *
     * @return [Response] со списком категорий ([ArticlesDTO]) или ошибкой.
     */
    @GET("categories")
    suspend fun getArticles(): Response<List<ArticlesDTO>>

    /**
     * Получить список аккаунтов пользователя.
     *
     * @return [Response] со списком аккаунтов ([AccountDTO]) или ошибкой.
     */
    @GET("accounts")
    suspend fun getAccount(): Response<List<AccountDTO>>

    /**
     * Получить аккаунт по id.
     *
     * @param id Идентификатор аккаунта.
     * @return [Response] с аккаунтом ([AccountDTO]) или ошибкой.
     */
    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): Response<AccountDTO>

    @PUT("accounts/{id}")
    suspend fun editAccount(
        @Path("id") id: Int,
        @Body accountUpdateRequest: AccountUpdateRequestDTO
    ): Response<Unit>

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(@Path("id") id: Int): Response<Unit>

    @POST("transactions")
    suspend fun createTransaction(
        @Body createTransactionDTO: CreateTransactionDTO
    ): Response<Unit>

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): Response<TransactionDTO>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body createTransactionDTO: CreateTransactionDTO
    ): Response<Unit>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Response<Unit>
}
