package com.finance.data.repository

import android.util.Log
import com.finance.data.mapper.IncomeDocumentSnapshotMapper
import com.finance.domain.model.Income
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.repository.IncomesRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    private val incomeDocumentSnapshotMapper: IncomeDocumentSnapshotMapper
) : IncomesRepository {

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

    override fun getAllIncomesFromDb(callback: (List<Income>?) -> Unit) {
        val db = Firebase.firestore
        Firebase.auth.uid?.let { uid ->
            db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_INCOMES)
                .addSnapshotListener { queryDocumentSnapshots, e ->
                    if (e != null) {
                        Log.e("MyLog", "ERROR: ${e.message}")
                        callback.invoke(null)
                        return@addSnapshotListener
                    }

                    val listOfExpenses = mutableListOf<Income>()
                    if (queryDocumentSnapshots != null) {
                        for (document in queryDocumentSnapshots) {
                            val expense = incomeDocumentSnapshotMapper.mapTo(document)
                            listOfExpenses.add(expense)
                        }
                    }
                    callback.invoke(listOfExpenses)
                }
        }
    }

    companion object {
        const val COLLECTION_INCOMES = "incomes"
        const val COLLECTION_USERS = "users"
    }
}