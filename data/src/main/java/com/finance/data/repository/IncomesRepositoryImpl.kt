package com.finance.data.repository

import android.util.Log
import com.finance.data.mapper.IncomeDocumentSnapshotMapper
import com.finance.domain.FIELD_DATE_LONG
import com.finance.domain.model.Income
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.repository.IncomesRepository
import com.finance.domain.repository.TransactionReceiveState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    private val incomeDocumentSnapshotMapper: IncomeDocumentSnapshotMapper
) : IncomesRepository {

    private var registration: ListenerRegistration? = null

    override fun addIncomeToDb(model: Income, callback: (IncomeLogState) -> Unit) {
        val db = Firebase.firestore
        Firebase.auth.uid?.let { uid ->
            db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_INCOMES)
                .document()
                .set(model)
                .addOnSuccessListener {
                    callback.invoke(IncomeLogState.SuccessIncomeLogState)
                }
                .addOnFailureListener {
                    Log.e("MyLog", "ERROR: ${it.message}")
                    callback.invoke(IncomeLogState.FailureIncomeLogState(it.message))
                }
        }
    }

    override fun getAllIncomesFromDb(
        startDate: Long,
        endDate: Long,
        callback: (TransactionReceiveState) -> Unit
    ) {
        val db = Firebase.firestore
        callback.invoke(TransactionReceiveState.Loading)
        Firebase.auth.uid?.let { uid ->
            val query = db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_INCOMES)
                .whereLessThan(FIELD_DATE_LONG, endDate)
                .whereGreaterThan(FIELD_DATE_LONG, startDate)

            registration?.remove()
            registration = query.addSnapshotListener { queryDocumentSnapshots, e ->
                if (e != null) {
                    Log.e("MyLog", "ERROR: ${e.message}")
                    callback.invoke(TransactionReceiveState.Error(e.message ?: ""))
                    return@addSnapshotListener
                }

                val listOfExpenses = mutableListOf<Income>()
                if (queryDocumentSnapshots != null) {
                    for (document in queryDocumentSnapshots) {
                        val expense = incomeDocumentSnapshotMapper.mapTo(document)
                        listOfExpenses.add(expense)
                    }
                }
                callback.invoke(TransactionReceiveState.Success(listOfExpenses))
            }
        }
    }

    companion object {
        const val COLLECTION_INCOMES = "incomes"
        const val COLLECTION_USERS = "users"
    }
}