package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO класс для передачи данных транзакции (Transaction), сериализуемый с помощью Kotlinx Serialization.
 *
 * Все поля nullable, чтобы корректно обрабатывать возможное отсутствие данных в API ответе.
 *
 * @property accountDTO Данные счета, связанные с транзакцией.
 * @property amount Сумма транзакции в виде строки.
 * @property categoryDTO Данные категории, связанной с транзакцией.
 * @property comment Комментарий к транзакции.
 * @property createdAt Дата и время создания транзакции (строка в формате ISO).
 * @property id Уникальный идентификатор транзакции.
 * @property transactionDate Дата совершения транзакции (строка в формате ISO).
 * @property updatedAt Дата и время последнего обновления транзакции.
 */
@Serializable
data class TransactionDTO(
    @SerialName("account")
    val accountDTO: AccountDTO? = null,
    @SerialName("amount")
    val amount: String? = null,
    @SerialName("category")
    val categoryDTO: CategoryDTO? = null,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("transactionDate")
    val transactionDate: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)
