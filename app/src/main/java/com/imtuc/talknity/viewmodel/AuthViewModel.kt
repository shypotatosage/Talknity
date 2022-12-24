package com.imtuc.talknity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.imtuc.talknity.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.await
import retrofit2.awaitResponse

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    val _register: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val register: LiveData<String>
        get() = _register

    fun registerUser(user_username: String, user_email: String, user_password: String)
            = viewModelScope.launch {
        repo.registerUser(user_username, user_email, user_password).let {
                response ->
            if (response.isSuccessful) {
                _register.value = "User Successfully Registered"
                Log.d("Register User", response.body().toString())
            } else {
                _register.value = response.message()
                Log.e("Register User", "Failed!")
            }
        }
    }

    fun resetRegister() {
        _register.value = null
    }

    val _login: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val login: LiveData<String>
        get() = _login

    fun loginUser(user_usernameemail: String, user_email: String, context: Context)
            = viewModelScope.launch {
        repo.loginUser(user_usernameemail, user_email).let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Login successful") {
                    _login.value = "Login Successful"
                    val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
                    var editor = preferences.edit()
                    editor.putString("user_username", response.body()?.get("user_username")?.asString)
                    editor.putString("user_displayname", response.body()?.get("user_displayname")?.asString)
                    editor.putString("user_email", response.body()?.get("user_email")?.asString)
                    editor.putString("user_image", response.body()?.get("user_image")?.asString)
                    editor.putInt("user_id", response.body()?.get("user_id")!!.asInt)

                    editor.commit()
                } else if (response.body()?.get("message")?.asString == "Password does not match!"){
                    _login.value = "Password Is Incorrect"
                } else if (response.body()?.get("message")?.asString == "Username/Email does not exist!") {
                    _login.value = "Username/Email Is Not Registered Yet"
                } else {
                    _login.value = "Login Failed"
                }

                Log.d("Login User", _login.value.toString())
            } else {
                Log.e("Login User", response.body()?.get("message")!!.asString)
            }
        }
    }

}