package com.imtuc.talknity.retrofit

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @FormUrlEncoded
    @POST("/get-ownedcommunities")
    suspend fun getOwnedCommunities(
        @Field("user_id") user_id: String,
    ): Response<JsonObject>

    @GET("/get-communitycategories")
    suspend fun getCommunityCategories(): Response<JsonObject>

    @GET("/get-postshome")
    suspend fun getPostHome(): Response<JsonObject>

    @Multipart
    @FormUrlEncoded
    @POST("/store-community")
    fun createCommunity(
        @Part("description") description: RequestBody,
        @Part community_logo: MultipartBody.Part?,
        @Field("community_name") community_name: String,
        @Field("community_description") community_description: String,
        @Field("community_contact") community_contact: String,
        @Field("category_id") category_id: String,
        @Field("leader_id") leader_id: String,
    ): Response<JsonObject>

}