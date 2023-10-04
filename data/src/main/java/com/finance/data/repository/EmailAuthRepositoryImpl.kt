package com.finance.data.repository

import com.finance.domain.repository.EmailAuthRepository
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.auth.ktx.auth
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class EmailAuthRepositoryImpl @Inject constructor(

) : EmailAuthRepository {

    override fun signUpWithEmail(email: String, password: String) = callbackFlow {
        val auth = Firebase.auth
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                trySendBlocking(task.isSuccessful)
//            }
        for (i in 1..200){
            delay(1000)
            trySendBlocking(Random.nextBoolean())
        }
        awaitClose { channel.close() }
    }
}