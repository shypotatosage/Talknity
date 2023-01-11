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

    @FormUrlEncoded
    @POST("/user-profile")
    suspend fun getProfile(
        @Field("user_id") user_id: String,
    ): Response<JsonObject>

    @GET("/get-communitieshome")
    suspend fun getCommunityHome(): Response<JsonObject>

    @GET("/get-ownedcommunities/{user_id}")
    suspend fun getOwnedCommunities(
        @Path("user_id") user_id: String,
    ): Response<JsonObject>

    @GET("/get-communitiescategory/{category_id}")
    suspend fun getCommunitiesCategory(
        @Path("category_id") category_id: String,
    ): Response<JsonObject>

    @GET("/search-communitiescategory/{category_id}/{search_key}")
    suspend fun searchCommunitiesCategory(
        @Path("category_id") category_id: String,
        @Path("search_key") search_key: String,
    ): Response<JsonObject>

    @GET("/get-communitycategories")
    suspend fun getCommunityCategories(): Response<JsonObject>

    @GET("/search-communitycategories/{search_key}")
    suspend fun searchCommunityCategories(
        @Path("search_key") search_key: String
    ): Response<JsonObject>

    @GET("/get-postshome")
    suspend fun getPostHome(): Response<JsonObject>

    @GET("/get-posts")
    suspend fun getPost(): Response<JsonObject>

    @GET("/get-ownedposts/{user_id}")
    suspend fun getOwnedPosts(
        @Path("user_id") user_id: String,
    ): Response<JsonObject>

    @GET("/search-posts/{search_key}")
    suspend fun searchPosts(
        @Path("search_key") search_key: String
    ): Response<JsonObject>

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

    @Multipart
    @POST("/store-post")
    fun createPostImage(
        @Part post_image: MultipartBody.Part,
        @Part("post_title") post_title: RequestBody,
        @Part("post_content") post_content: RequestBody,
        @Part("anonymous") anonymous: Boolean,
        @Part("uid") uid: RequestBody,
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/store-post")
    fun createPost(
        @Field("post_title") post_title: String,
        @Field("post_content") post_content: String,
        @Field("anonymous") anonymous: Boolean,
        @Field("uid") user_id: String,
    ): Response<JsonObject>

}