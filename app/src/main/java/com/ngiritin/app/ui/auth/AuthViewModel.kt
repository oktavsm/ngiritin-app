package com.ngiritin.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.ngiritin.app.data.repository.AuthRepository
import com.ngiritin.app.utils.Result

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _authResult = MutableLiveData<Result<FirebaseUser>>()
    val authResult: LiveData<Result<FirebaseUser>> = _authResult

    fun login(email: String, pass: String) {
        _authResult.value = Result.Loading
        authRepository.login(email, pass) { result ->
            _authResult.value = result
        }
    }

    fun register(name: String, email: String, pass: String) {
        _authResult.value = Result.Loading
        authRepository.register(name, email, pass) { result ->
            _authResult.value = result
        }
    }

    fun loginWithGoogle(credential: AuthCredential) {
        _authResult.value = Result.Loading
        authRepository.loginWithGoogle(credential) { result ->
            _authResult.value = result
        }
    }

    fun checkCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun logout() {
        authRepository.logout()
    }
}
