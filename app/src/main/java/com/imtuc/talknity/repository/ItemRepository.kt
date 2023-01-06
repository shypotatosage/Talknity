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

    suspend fun getCommunityMembers(community_id: String)
            = api.getCommunityMembers(community_id)

    suspend fun getCommunitiesCategory(cid: String)
            = api.getCommunitiesCategory(cid)

    suspend fun searchCommunitiesCategory(cid: String, search_key: String)
            = api.searchCommunitiesCategory(cid, search_key)

    suspend fun getCommunityCategories()
            = api.getCommunityCategories()

    suspend fun searchCommunityCategories(key: String)
            = api.searchCommunityCategories(key)

    suspend fun getPostHome()
            = api.getPostHome()

    suspend fun getPosts()
            = api.getPosts()

    suspend fun getPost(post_id: String)
            = api.getPost(post_id)

    suspend fun getOwnedPosts(uid: String)
            = api.getOwnedPosts(uid)

    suspend fun searchPosts(key: String)
            = api.searchPosts(key)

    suspend fun getOwnedComments(uid: String)
            = api.getOwnedComments(uid)

    suspend fun createPost(
        post_title: String,
        post_content: String,
        anonymous: Boolean,
        uid: String
    )
            = api.createPost(post_title, post_content, anonymous, uid)

    suspend fun createComment(
        comment_content: String,
        post_id: String,
        user_id: String
    )
            = api.createComment(comment_content, post_id, user_id)

    suspend fun removeMember(
        community_member_id: String
    )
            = api.removeMember(community_member_id)

    suspend fun signoutCommunity(
        community_id: String,
        user_id: String
    )
            = api.signoutCommunity(community_id, user_id)

    suspend fun joinCommunity(
        community_id: String,
        user_id: String
    )
            = api.joinCommunity(community_id, user_id)

    suspend fun deletePost(
        post_id: String
    )
            = api.deletePost(post_id)

    suspend fun deleteComment(
        comment_id: String
    )
            = api.deleteComment(comment_id)

    suspend fun updateComment(
        comment_content: String,
        comment_id: String
    )
            = api.updateComment(comment_content, comment_id)

    suspend fun deleteCommunity(
        community_id: String
    )
            = api.deleteCommunity(community_id)

}