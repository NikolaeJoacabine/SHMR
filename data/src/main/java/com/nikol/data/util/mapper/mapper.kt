package com.nikol.data.util.mapper

import com.nikol.data.model.AccountDTO
import com.nikol.data.model.AccountUpdateRequestDTO
import com.nikol.data.model.ArticlesDTO
import com.nikol.data.model.CreateTransactionDTO
import com.nikol.data.model.TransactionDTO
import com.nikol.data.util.formater.formatAmountToInt
import com.nikol.data.util.formater.parseCreatedAt
import com.nikol.domain.model.Account
import com.nikol.domain.model.AccountType
import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.model.Articles
import com.nikol.domain.model.CategoryType
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionDetail
import com.nikol.domain.model.UpdateTransaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

/**
 * Преобразует объект [TransactionDTO] в доменную модель [Transaction].
 *
 * Заполняет все поля, конвертируя необходимые значения, например,
 * сумму и дату создания.
 *
 * @return объект [Transaction] с данными из [TransactionDTO].
 */
fun TransactionDTO.toDomain(): Transaction {
    return Transaction(
        id = id ?: 1,
        category = categoryDTO?.name ?: "",
        comment = if (comment == "") null else comment,
        emoji = categoryDTO?.emoji ?: "",
        amount = formatAmountToInt(amount),
        isIncome = categoryDTO?.isIncome == true,
        createdAt = parseCreatedAt(createdAt)
    )
}

/**
 * Преобразует объект [ArticlesDTO] в доменную модель [Articles].
 *
 * Заполняет поля с учётом возможных null значений,
 * обеспечивая дефолтные значения.
 *
 * @return объект [Articles] с данными из [ArticlesDTO].
 */
fun ArticlesDTO.toDomain(): Articles {
    return Articles(
        id = id ?: 1,
        name = name ?: "",
        emoji = emoji ?: "",
        isIncome = isIncome == true
    )
}

/**
 * Преобразует объект [AccountDTO] в доменную модель [Account].
 *
 * Заполняет поля, конвертируя сумму и устанавливая дефолтные значения,
 * если некоторые поля отсутствуют.
 *
 * @return объект [Account] с данными из [AccountDTO].
 */
fun AccountDTO.toDomain(): Account {
    return Account(
        id = id ?: 1,
        userId = userId ?: 1,
        name = name ?: "",
        emoji = "\uD83D\uDCB0",
        balance = formatAmountToInt(balance),
        currency = currency ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: ""
    )
}

fun AccountUpdateRequest.toDTO(): AccountUpdateRequestDTO {
    return AccountUpdateRequestDTO(
        name = name,
        balance = balance.toString(),
        currency = "RUB"
    )
}

fun CreateTransaction.toData(): CreateTransactionDTO {
    val isoDate = transactionDate
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toString()

    return CreateTransactionDTO(
        accountId = accountId,
        categoryId = categoryId,
        amount = String.format(Locale.US, "%.2f", amount.toDouble()),
        transactionDate = isoDate,
        comment = if (comment == "") null else comment
    )
}

fun TransactionDTO.toDomainDetail(): TransactionDetail {
    return TransactionDetail(
        id = id ?: -1,
        incomeType = CategoryType(
            id = categoryDTO?.id ?: -1,
            name = categoryDTO?.name ?: "Без категории"
        ),
        accountType = AccountType(
            id = accountDTO?.id ?: -1,
            name = accountDTO?.name ?: "Без названия"
        ),
        comment = comment,
        amount = amount?.toDoubleOrNull()?.toInt()?.toString() ?: "0",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: ""
    )
}

fun UpdateTransaction.toData(): CreateTransactionDTO {
    val isoDate = transactionDate
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toString()

    return CreateTransactionDTO(
        accountId = accountId,
        categoryId = categoryId,
        amount = String.format(Locale.US, "%.2f", amount.toDouble()),
        transactionDate = isoDate,
        comment = if (comment == "") null else comment
    )
}
