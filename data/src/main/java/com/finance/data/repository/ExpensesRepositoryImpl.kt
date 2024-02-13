package com.finance.data.repository

import android.util.Log
import com.finance.data.mapper.ExpenseDocumentSnapshotMapper
import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.repository.ExpensesRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expenseDocumentSnapshotMapper: ExpenseDocumentSnapshotMapper
) : ExpensesRepository {

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

    override fun getAllExpensesFromDb(callback: (List<Expense>?) -> Unit) {
        val db = Firebase.firestore
        Firebase.auth.uid?.let { uid ->
            db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_EXPENSES)
                .addSnapshotListener { queryDocumentSnapshots, e ->
                    if (e != null) {
                        Log.e("MyLog", "ERROR: ${e.message}")
                        callback.invoke(null)
                        return@addSnapshotListener
                    }

                    val listOfExpenses = mutableListOf<Expense>()
                    if (queryDocumentSnapshots != null) {
                        for (document in queryDocumentSnapshots) {
                            val expense = expenseDocumentSnapshotMapper.mapTo(document)
                            listOfExpenses.add(expense)
                        }
                    }
                    callback.invoke(listOfExpenses)
                }
        }
    }

    companion object {
        const val COLLECTION_EXPENSES = "expenses"
        const val COLLECTION_USERS = "users"
    }
}