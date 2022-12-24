package com.imtuc.talknity.retrofit

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface EndPointAPI {

    @FormUrlEncoded
    @POST("/register-user")
    suspend fun register(
        @Field("user_username") user_username: String,
        @Field("user_email") user_email: String,
        @Field("user_password") user_password: String,
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST("/login-user")
    suspend fun login(
        @Field("user_usernameemail") user_usernameemail: String,
        @Field("user_password") user_password: String,
    ): Response<JsonObject>

    @GET("/get-communitieshome")
    suspend fun getCommunityHome(): Response<JsonObject>

    @GET("/get-postshome")
    suspend fun getPostHome(): Response<JsonObject>

}