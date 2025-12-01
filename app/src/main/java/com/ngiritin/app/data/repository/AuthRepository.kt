package com.ngiritin.app.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.ngiritin.app.data.model.User
import com.ngiritin.app.utils.Result
import java.util.Date

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun login(email: String, pass: String, onResult: (Result<FirebaseUser>) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                onResult(Result.Success(it.user!!))
            }
            .addOnFailureListener {
                onResult(Result.Error(it.message ?: "Login failed"))
            }
    }

    fun register(name: String, email: String, pass: String, onResult: (Result<FirebaseUser>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserToFirestore(firebaseUser, name) { _ ->
                            onResult(Result.Success(firebaseUser))
                        }
                    } else {
                        saveUserToFirestore(firebaseUser, name) { _ ->
                            onResult(Result.Success(firebaseUser))
                        }
                    }
                }
            }
            .addOnFailureListener {
                onResult(Result.Error(it.message ?: "Registration failed"))
            }
    }

    fun loginWithGoogle(credential: AuthCredential, onResult: (Result<FirebaseUser>) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                if (user != null) {
                    val docRef = firestore.collection("users").document(user.uid)
                    docRef.get().addOnSuccessListener { document ->
                        if (document.exists()) {
                            onResult(Result.Success(user))
                        } else {
                            val name = user.displayName ?: "Unknown User"
                            saveUserToFirestore(user, name) {
                                onResult(Result.Success(user))
                            }
                        }
                    }.addOnFailureListener {
                        onResult(Result.Error("Failed to check user data in Firestore"))
                    }
                } else {
                    onResult(Result.Error("Failed to retrieve Google user data"))
                }
            }
            .addOnFailureListener {
                onResult(Result.Error(it.message ?: "Google Sign-In failed"))
            }
    }

    private fun saveUserToFirestore(firebaseUser: FirebaseUser, name: String, onComplete: (Boolean) -> Unit) {
        val user = User(
            id = firebaseUser.uid,
            name = name,
            email = firebaseUser.email ?: "",
            photoUrl = firebaseUser.photoUrl?.toString() ?: "",
            createdAt = Date(),
            updatedAt = Date()
        )

        firestore.collection("users")
            .document(firebaseUser.uid)
            .set(user)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun logout() {
        auth.signOut()
    }
}
