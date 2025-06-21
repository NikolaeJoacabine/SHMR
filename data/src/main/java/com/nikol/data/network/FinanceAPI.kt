package com.nikol.data.network

import com.nikol.data.model.AccountDTO
import com.nikol.data.model.ArticlesDTO
import com.nikol.data.model.TransactionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FinanceAPI {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionBeginningOfTheMonth(@Path("accountId") accountId: Int = 218): Response<List<TransactionDTO>>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionForPeriod(
        @Path("accountId") accountId: Int = 218,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<TransactionDTO>>

    @GET("categories")
    suspend fun getArticles(): Response<List<ArticlesDTO>>

    @GET("accounts")
    suspend fun getAccount(): Response<List<AccountDTO>>
}