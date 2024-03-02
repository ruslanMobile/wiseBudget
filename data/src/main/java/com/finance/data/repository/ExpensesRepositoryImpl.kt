package com.finance.data.repository

import android.util.Log
import com.finance.data.mapper.ExpenseDocumentSnapshotMapper
import com.finance.domain.FIELD_DATE_LONG
import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.repository.ExpensesRepository
import com.finance.domain.repository.TransactionReceiveState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expenseDocumentSnapshotMapper: ExpenseDocumentSnapshotMapper
) : ExpensesRepository {

    private var registration: ListenerRegistration? = null

    override fun addExpenseToDb(model: Expense, callback: (ExpenseLogState) -> Unit) {
        val db = Firebase.firestore
        Firebase.auth.uid?.let { uid ->
            db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_EXPENSES)
                .document()
                .set(model)
                .addOnSuccessListener {
                    callback.invoke(ExpenseLogState.SuccessExpenseLogState)
                }
                .addOnFailureListener {
                    Log.e("MyLog", "ERROR: ${it.message}")
                    callback.invoke(ExpenseLogState.FailureExpenseLogState(it.message))
                }
        }
    }

    override fun getAllExpensesFromDb(
        startDate: Long,
        endDate: Long,
        callback: (TransactionReceiveState) -> Unit
    ) {
        val db = Firebase.firestore
        callback.invoke(TransactionReceiveState.Loading)
        Firebase.auth.uid?.let { uid ->
            val query = db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_EXPENSES)
                .whereLessThan(FIELD_DATE_LONG, endDate)
                .whereGreaterThan(FIELD_DATE_LONG, startDate)

            registration?.remove()
            registration = query.addSnapshotListener { queryDocumentSnapshots, e ->
                if (e != null) {
                    Log.e("MyLog", "ERROR: ${e.message}")
                    callback.invoke(TransactionReceiveState.Error(e.message ?: ""))
                    return@addSnapshotListener
                }

                val listOfExpenses = mutableListOf<Expense>()
                if (queryDocumentSnapshots != null) {
                    for (document in queryDocumentSnapshots) {
                        val expense = expenseDocumentSnapshotMapper.mapTo(document)
                        listOfExpenses.add(expense)
                    }
                }
                callback.invoke(TransactionReceiveState.Success(listOfExpenses))
            }
        }
    }

    companion object {
        const val COLLECTION_EXPENSES = "expenses"
        const val COLLECTION_USERS = "users"
    }
}