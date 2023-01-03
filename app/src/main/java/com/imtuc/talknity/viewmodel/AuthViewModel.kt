package com.imtuc.talknity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.imtuc.talknity.model.User
import com.imtuc.talknity.repository.AuthRepository
import com.imtuc.talknity.retrofit.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
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
                Log.e("Register User", response.message())
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
//                    editor.putString("user_username", response.body()?.get("user_username")?.asString)
//                    editor.putString("user_displayname", response.body()?.get("user_displayname")?.asString)
//                    editor.putString("user_email", response.body()?.get("user_email")?.asString)
//                    editor.putString("user_image", response.body()?.get("user_image")?.asString)
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
                Log.e("Login User", response.message())
            }
        }
    }

    val _profile: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    val profile: LiveData<User>
        get() = _profile

    fun getProfile(uid: String)
            = viewModelScope.launch {
        repo.getProfile(uid).let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    var user = response.body()!!.get("data")

                    var user_id = user.asJsonObject["id"].asString
                    var user_username = user.asJsonObject["user_username"].asString
                    var user_displayname = user.asJsonObject["user_displayname"].asString
                    var user_email = user.asJsonObject["user_email"].asString
                    var user_image = user.asJsonObject["user_image"].asString

                    _profile.value = User(
                        user_id,
                        user_username,
                        user_displayname,
                        user_email,
                        user_image
                    )
                }

                Log.d("User Profile", response.body().toString())
            } else {
                Log.e("User Profile Error", response.message())
            }
        }
    }

    val _profileUpdate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val profileUpdate: LiveData<String>
        get() = _profileUpdate

    fun updateProfile(
        user_image: MultipartBody.Part?,
        user_username: RequestBody,
        user_displayname: RequestBody,
        user_email: RequestBody,
        user_password: RequestBody,
        user_id: RequestBody
    ) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.updateProfile(
                    user_image,
                    user_displayname,
                    user_username,
                    user_email,
                    user_password,
                    user_id
                ).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _profileUpdate.value = "Profile Successfully Updated"
                    } else {
                        if (response.message() == "Bad Request") {
                            _profileUpdate.value = "Password does not match!"
                        } else {
                            _profileUpdate.value = response.message()
                        }
                    }

                    Log.d("Update User Profile", response.message())
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun resetProfileUpdate() {
        _profileUpdate.value = ""
    }

}