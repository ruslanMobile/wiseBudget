package com.finance.data.mapper

import com.finance.domain.model.Income
import com.google.firebase.firestore.DocumentSnapshot

class IncomeDocumentSnapshotMapper : Mapper<Income, DocumentSnapshot>() {
    override fun mapTo(item: DocumentSnapshot): Income {
        return Income(
            incomeName = item["incomeName"] as? String,
            category = item["category"] as? String,
            amount = (item["amount"] as? Number)?.toInt(),
            dateLong = (item["dateLong"] as? Number)?.toLong(),
        )
    }

    override fun mapFrom(item: Income): DocumentSnapshot {
        TODO("Not yet implemented")
    }
}