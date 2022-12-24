package com.imtuc.talknity.repository

import com.imtuc.talknity.retrofit.EndPointAPI
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: EndPointAPI
) {

    suspend fun registerUser(
        user_username: String,
        user_email: String,
        user_password: String)
            = api.register(user_username, user_email, user_password)

    suspend fun loginUser(
        user_usernameemail: String,
        user_email: String)
            = api.login(user_usernameemail, user_email)

}