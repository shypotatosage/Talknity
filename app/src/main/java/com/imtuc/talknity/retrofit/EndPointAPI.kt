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

    @GET("/get-communitymembers/{community_id}")
    suspend fun getCommunityMembers(
        @Path("community_id") community_id: String
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
    suspend fun getPosts(): Response<JsonObject>

    @GET("/get-post/{post_id}")
    suspend fun getPost(
        @Path("post_id") post_id: String
    ): Response<JsonObject>

    @GET("/get-ownedposts/{user_id}")
    suspend fun getOwnedPosts(
        @Path("user_id") user_id: String,
    ): Response<JsonObject>

    @GET("/search-posts/{search_key}")
    suspend fun searchPosts(
        @Path("search_key") search_key: String
    ): Response<JsonObject>

    @GET("/get-ownedcomments/{user_id}")
    suspend fun getOwnedComments(
        @Path("user_id") user_id: String,
    ): Response<JsonObject>

    @Multipart
    @POST("/store-community")
    fun createCommunity(
        @Part community_logo: MultipartBody.Part,
        @Part("community_name") community_name: RequestBody,
        @Part("community_description") community_description: RequestBody,
        @Part("community_contact") community_contact: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part("leader_id") leader_id: RequestBody,
    ): Call<ResponseBody>

    @Multipart
    @POST("/store-post")
    fun createPostImage(
        @Part post_image: MultipartBody.Part?,
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

    @FormUrlEncoded
    @POST("/store-comment")
    suspend fun createComment(
        @Field("comment_content") comment_content: String,
        @Field("post_id") post_id: String,
        @Field("user_id") user_id: String,
    ): Response<JsonObject>

    @Multipart
    @PATCH("/update-profile")
    fun updateProfile(
        @Part user_image: MultipartBody.Part?,
        @Part("user_displayname") user_displayname: RequestBody,
        @Part("user_username") user_username: RequestBody,
        @Part("user_email") user_email: RequestBody,
        @Part("user_password") user_password: RequestBody,
        @Part("user_id") user_id: RequestBody,
    ): Call<JsonObject>

    @DELETE("/remove-member/{community_member_id}")
    suspend fun removeMember(
        @Path("community_member_id") community_member_id: String,
    ): Response<JsonObject>

    @DELETE("/signout-community/{user_id}/{community_id}")
    suspend fun signoutCommunity(
        @Path("community_id") community_id: String,
        @Path("user_id") user_id: String,
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST("/join-community")
    suspend fun joinCommunity(
        @Field("community_id") community_id: String,
        @Field("user_id") user_id: String,
    ): Response<JsonObject>

    @Multipart
    @PATCH("/update-post")
    fun updatePost(
        @Part post_image: MultipartBody.Part?,
        @Part("post_title") post_title: RequestBody,
        @Part("post_content") post_content: RequestBody,
        @Part("anonymous") anonymous: Boolean,
        @Part("post_id") post_id: RequestBody,
    ): Call<JsonObject>

    @DELETE("/delete-post/{post_id}")
    suspend fun deletePost(
        @Path("post_id") post_id: String,
    ): Response<JsonObject>

    @DELETE("/delete-comment/{comment_id}")
    suspend fun deleteComment(
        @Path("comment_id") comment_id: String,
    ): Response<JsonObject>

    @FormUrlEncoded
    @PATCH("/update-comment")
    fun updateComment(
        @Field("comment_content") comment_content: String,
        @Field("comment_id") comment_id: String,
    ): Call<JsonObject>

    @Multipart
    @PATCH("/update-community")
    fun updateCommunity(
        @Part community_logo: MultipartBody.Part?,
        @Part("community_name") community_name: RequestBody,
        @Part("community_description") community_description: RequestBody,
        @Part("community_contact") community_contact: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part("community_id") community_id: RequestBody,
    ): Call<JsonObject>

    @DELETE("/delete-community/{community_id}")
    suspend fun deleteCommunity(
        @Path("community_id") community_id: String,
    ): Response<JsonObject>

}