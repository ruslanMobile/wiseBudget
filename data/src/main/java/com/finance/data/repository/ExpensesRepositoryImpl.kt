package com.finance.data.repository

import android.util.Log
import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpensesRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(

) : ExpensesRepository {

    override fun addExpenseToDb(model: Expense) = callbackFlow {
        val db = Firebase.firestore
        Firebase.auth.uid?.let { uid ->
            db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_EXPENSES)
                .document()
                .set(model)
                .addOnSuccessListener {
                    trySendBlocking(true)
                }
                .addOnFailureListener {
                    Log.e("MyLog", "ERROR: ${it.message}")
                    trySendBlocking(false)
                }
        }

        awaitClose { channel.close() }
    }

    companion object {
        const val COLLECTION_EXPENSES = "expenses"
        const val COLLECTION_USERS = "users"
    }
}