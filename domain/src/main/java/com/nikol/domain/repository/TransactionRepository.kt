package com.nikol.domain.repository

import com.nikol.domain.state.TransactionState

interface TransactionRepository {

    suspend fun getTransaction(): TransactionState

}