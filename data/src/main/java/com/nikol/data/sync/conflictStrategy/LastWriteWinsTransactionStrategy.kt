package com.nikol.data.sync.conflictStrategy

import com.nikol.data.transaction.local.database.TransactionEntity
import javax.inject.Inject

class LastWriteWinsTransactionStrategy @Inject constructor() : SyncConflictStrategy<TransactionEntity> {
    override fun resolveConflict(
        local: TransactionEntity,
        remote: TransactionEntity
    ): TransactionEntity {
        return if (local.updatedAtFromServer.isAfter(remote.updatedAtFromServer)) local else remote
    }
}
