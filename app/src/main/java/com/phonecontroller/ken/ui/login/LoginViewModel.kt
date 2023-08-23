package com.phonecontroller.ken.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phonecontroller.ken.R
import com.phonecontroller.ken.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginFormResult = MutableLiveData<LoginFormResult>()
    val loginFormResult: LiveData<LoginFormResult> = _loginFormResult

    init {
        if (loginRepository.fireBaseAuth.currentUser != null){
            _loginFormResult.value = LoginFormResult(success = R.string.login_success)
        }
    }

    suspend fun login( username: String, password: String) = coroutineScope {

        val result = try {
            loginRepository.login(username, password).await()
            LoginFormResult(success = R.string.login_success)
        }
        catch (e : Throwable){
            LoginFormResult(success = R.string.login_failed)
        }
        withContext(Dispatchers.Main){
            _loginFormResult.value = result
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}