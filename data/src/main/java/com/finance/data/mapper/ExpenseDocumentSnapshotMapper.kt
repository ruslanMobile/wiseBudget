package com.finance.data.mapper

import com.finance.domain.model.Expense
import com.google.firebase.firestore.DocumentSnapshot

class ExpenseDocumentSnapshotMapper : Mapper<Expense, DocumentSnapshot>() {
    override fun mapTo(item: DocumentSnapshot): Expense {
        return Expense(
            expenseName = item["expenseName"] as? String,
            category = item["category"] as? String,
            amount = (item["amount"] as? Number)?.toInt(),
            dateLong = (item["dateLong"] as? Number)?.toLong(),
        )
    }

    override fun mapFrom(item: Expense): DocumentSnapshot {
        TODO("Not yet implemented")
    }
}