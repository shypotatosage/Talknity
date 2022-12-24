package com.imtuc.talknity.repository

import com.imtuc.talknity.retrofit.EndPointAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ItemRepository@Inject constructor(
    private val api: EndPointAPI
) {

    suspend fun getCommunityHome()
            = api.getCommunityHome()

    suspend fun getOwnedCommunities(uid: String)
            = api.getOwnedCommunities(uid)

    suspend fun getCommunityCategories()
            = api.getCommunityCategories()

    suspend fun searchCommunityCategories(key: String)
            = api.searchCommunityCategories(key)

    suspend fun getPostHome()
            = api.getPostHome()

    suspend fun getPost()
            = api.getPost()

    suspend fun getOwnedPosts(uid: String)
            = api.getOwnedPosts(uid)

    suspend fun searchPosts(key: String)
            = api.searchPosts(key)

    suspend fun createCommunity(
        desc: RequestBody,
        community_logo: MultipartBody.Part?,
        community_name: String,
        community_description: String,
        community_contact: String,
        category_id: String,
        leader_id: String
    )
            = api.createCommunity(desc, community_logo, community_name, community_description, community_contact, category_id, leader_id)

    suspend fun createPost(
        post_title: String,
        post_content: String,
        anonymous: Boolean,
        uid: String
    )
            = api.createPost(post_title, post_content, anonymous, uid)

}