package com.imtuc.talknity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.imtuc.talknity.model.*
import com.imtuc.talknity.repository.ItemRepository
import com.imtuc.talknity.retrofit.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.http.Multipart
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel() {
    val _posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>()
    }

    val posts: LiveData<List<Post>>
        get() = _posts

    val _posterror: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val posterror: LiveData<String>
        get() = _posterror

    fun getPosts() = viewModelScope.launch {
        repo.getPosts().let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _posterror.value = "Get Data Successful"
                    _posts.value = arrayListOf()

                    var tmpArrList = arrayListOf<Post>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var post_id = item.asJsonObject["id"].asString
                            var post_title = item.asJsonObject["post_title"].asString
                            var post_content = item.asJsonObject["post_content"].asString
                            var post_image = item.asJsonObject["post_image"].asString
                            var anonymous = item.asJsonObject["anonymous"].asString.toBoolean()
                            var created_at = item.asJsonObject["created_at"].asString
                            var comment_count = item.asJsonObject["comment_count"].asString

                            var user = item.asJsonObject["user"]

                            var user_id = user.asJsonObject["id"].asString
                            var user_username = user.asJsonObject["user_username"].asString
                            var user_displayname = user.asJsonObject["user_displayname"].asString
                            var user_email = user.asJsonObject["user_email"].asString
                            var user_image = user.asJsonObject["user_image"].asString

                            var userModel = User(
                                user_id,
                                user_username,
                                user_displayname,
                                user_email,
                                user_image
                            )

                            var post = Post(
                                post_id,
                                post_title,
                                post_content,
                                post_image,
                                anonymous,
                                created_at,
                                userModel,
                                comment_count
                            )

                            tmpArrList.add(post)
                        }
                    }

                    _posts.value = tmpArrList

                    Log.e("Post Data", _posts.value.toString())
                } else {
                    _posterror.value = response.message()
                }

                Log.d("Get Post Data", _posterror.value.toString())
            } else {
                Log.e("Get Post Data Error", response.message())
            }
        }
    }

    val _ownedPosts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>()
    }

    val ownedPosts: LiveData<List<Post>>
        get() = _ownedPosts

    val _ownedPostsError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val ownedPostsError: LiveData<String>
        get() = _ownedPostsError

    fun getOwnedPosts(uid: String) = viewModelScope.launch {
        repo.getOwnedPosts(uid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _ownedPostsError.value = "Get Data Successful"
                    _ownedPosts.value = arrayListOf()

                    var tmpArrList = arrayListOf<Post>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var post_id = item.asJsonObject["id"].asString
                            var post_title = item.asJsonObject["post_title"].asString
                            var post_content = item.asJsonObject["post_content"].asString
                            var post_image = item.asJsonObject["post_image"].asString
                            var anonymous = item.asJsonObject["anonymous"].asString.toBoolean()
                            var created_at = item.asJsonObject["created_at"].asString
                            var comment_count = item.asJsonObject["comment_count"].asString

                            var user = item.asJsonObject["user"]

                            var user_id = user.asJsonObject["id"].asString
                            var user_username = user.asJsonObject["user_username"].asString
                            var user_displayname = user.asJsonObject["user_displayname"].asString
                            var user_email = user.asJsonObject["user_email"].asString
                            var user_image = user.asJsonObject["user_image"].asString

                            var userModel = User(
                                user_id,
                                user_username,
                                user_displayname,
                                user_email,
                                user_image
                            )

                            var post = Post(
                                post_id,
                                post_title,
                                post_content,
                                post_image,
                                anonymous,
                                created_at,
                                userModel,
                                comment_count
                            )

                            tmpArrList.add(post)
                        }
                    }

                    _ownedPosts.value = tmpArrList

                    Log.e("Post Data", _ownedPosts.value.toString())
                } else {
                    _ownedPostsError.value = response.message()
                }

                Log.d("Get Post Data", _ownedPostsError.value.toString())
            } else {
                Log.e("Get Post Data Error", response.message())
            }
        }
    }

    fun searchPosts(key: String) = viewModelScope.launch {
        repo.searchPosts(key).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _posterror.value = "Get Data Successful"
                    _posts.value = arrayListOf()

                    var tmpArrList = arrayListOf<Post>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var post_id = item.asJsonObject["id"].asString
                            var post_title = item.asJsonObject["post_title"].asString
                            var post_content = item.asJsonObject["post_content"].asString
                            var post_image = item.asJsonObject["post_image"].asString
                            var anonymous = item.asJsonObject["anonymous"].asString.toBoolean()
                            var created_at = item.asJsonObject["created_at"].asString
                            var comment_count = item.asJsonObject["comment_count"].asString

                            var user = item.asJsonObject["user"]

                            var user_id = user.asJsonObject["id"].asString
                            var user_username = user.asJsonObject["user_username"].asString
                            var user_displayname = user.asJsonObject["user_displayname"].asString
                            var user_email = user.asJsonObject["user_email"].asString
                            var user_image = user.asJsonObject["user_image"].asString

                            var userModel = User(
                                user_id,
                                user_username,
                                user_displayname,
                                user_email,
                                user_image
                            )

                            var post = Post(
                                post_id,
                                post_title,
                                post_content,
                                post_image,
                                anonymous,
                                created_at,
                                userModel,
                                comment_count
                            )

                            tmpArrList.add(post)
                        }
                    }

                    _posts.value = tmpArrList

                    Log.e("Post Data", _posts.value.toString())
                } else {
                    _posterror.value = response.message()
                }

                Log.d("Search Post Data", _posterror.value.toString())
            } else {
                Log.e("Search Post Data Error", response.message())
            }
        }
    }

    val _postcreate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val postcreate: LiveData<String>
        get() = _postcreate

    fun createPost(post_title: String, post_content: String, anonymous: Boolean, user_id: String) =
        viewModelScope.launch {
            repo.createPost(post_title, post_content, anonymous, user_id).let { response ->
                if (response.isSuccessful) {
                    if (response.body()?.get("message")?.asString == "Success") {
                        _postcreate.value = "Discussion Successfully Created"
                    } else {
                        _postcreate.value = "Failed To Create Discussion\n" + response.message()
                    }

                    Log.d("Create Post", _postcreate.value.toString())
                } else {
                    Log.e("Create Post Error", response.message())
                }
            }
        }

    fun createPostImage(
        post_image: MultipartBody.Part?,
        post_title: RequestBody,
        post_content: RequestBody,
        anonymous: Boolean,
        user_id: RequestBody
    ) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.createPostImage(
                    post_image,
                    post_title,
                    post_content,
                    anonymous,
                    user_id
                ).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _postcreate.value = "Discussion Successfully Created"
                    } else {
                        _postcreate.value = "Failed To Create Discussion\n" + response.raw().message
                    }

                    Log.d("Create Post Image", _postcreate.value!!)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun resetPostCreate() {
        _postcreate.value = null
    }

    val _postCommentError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val postCommentError: LiveData<String>
        get() = _postCommentError

    val _postComment: MutableLiveData<PostComment> by lazy {
        MutableLiveData<PostComment>()
    }

    val postComment: LiveData<PostComment>
        get() = _postComment

    fun getPost(post_id: String) = viewModelScope.launch {
        repo.getPost(post_id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val data: JsonObject = response.body()!!.getAsJsonObject("data")

                        var post_id = data.asJsonObject["id"].asString
                        var post_title = data.asJsonObject["post_title"].asString
                        var post_content = data.asJsonObject["post_content"].asString
                        var post_image = data.asJsonObject["post_image"].asString
                        var anonymous = data.asJsonObject["anonymous"].asString.toBoolean()
                        var created_at = data.asJsonObject["created_at"].asString

                        var user = data.asJsonObject["user"]

                        var user_id = user.asJsonObject["id"].asString
                        var user_username = user.asJsonObject["user_username"].asString
                        var user_displayname = user.asJsonObject["user_displayname"].asString
                        var user_email = user.asJsonObject["user_email"].asString
                        var user_image = user.asJsonObject["user_image"].asString

                        var userModel = User(
                            user_id,
                            user_username,
                            user_displayname,
                            user_email,
                            user_image
                        )

                        var tmpArrList = arrayListOf<Comment>()

                        if (!response.body()!!
                                .get("data").asJsonObject.get("comments").isJsonNull
                        ) {
                            var comments: JsonArray = data.getAsJsonArray("comments")

                            for (item in comments) {
                                var comment_id = item.asJsonObject["id"].asString
                                var comment_content = item.asJsonObject["comment_content"].asString
                                var comment_created_at = item.asJsonObject["created_at"].asString

                                var usr = item.asJsonObject["user"]

                                var usr_id = usr.asJsonObject["id"].asString
                                var usr_username = usr.asJsonObject["user_username"].asString
                                var usr_displayname = usr.asJsonObject["user_displayname"].asString
                                var usr_email = usr.asJsonObject["user_email"].asString
                                var usr_image = usr.asJsonObject["user_image"].asString

                                var userComment = User(
                                    usr_id,
                                    usr_username,
                                    usr_displayname,
                                    usr_email,
                                    usr_image
                                )

                                var comment = Comment(
                                    comment_id,
                                    comment_content,
                                    comment_created_at,
                                    userComment
                                )

                                tmpArrList.add(comment)
                            }
                        }

                        var postComment = PostComment(
                            post_id,
                            post_title,
                            post_content,
                            post_image,
                            anonymous,
                            created_at,
                            userModel,
                            tmpArrList
                        )

                        _postComment.value = postComment
                    }

                    _postCommentError.value = "Success"

                    Log.e("Post Data", _postComment.value.toString())
                } else {
                    _postCommentError.value = response.message()
                }

                Log.d("Get Post Comment Data", _postCommentError.value.toString())
            } else {
                Log.e("Get Post Comment Data Error", response.message())
            }
        }
    }

    val _commentCreate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val commentCreate: LiveData<String>
        get() = _commentCreate

    fun createComment(comment_content: String, post_id: String, user_id: String) =
        viewModelScope.launch {
            repo.createComment(comment_content, post_id, user_id).let { response ->
                if (response.isSuccessful) {
                    if (response.body()?.get("message")?.asString == "Success") {
                        _commentCreate.value = "Comment Successfully Submitted"
                    } else {
                        _commentCreate.value = "Failed To Create Discussion\n" + response.message()
                    }

                    Log.d("Create Comment", _commentCreate.value.toString())
                } else {
                    Log.e("Create Comment Error", response.message())
                }
            }
        }

    val _getPostUpdateError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getPostUpdateError: LiveData<String>
        get() = _getPostUpdateError

    val _getPostUpdate: MutableLiveData<Post> by lazy {
        MutableLiveData<Post>()
    }

    val getPostUpdate: LiveData<Post>
        get() = _getPostUpdate

    fun getPostUpdate(post_id: String) = viewModelScope.launch {
        repo.getPost(post_id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val data: JsonObject = response.body()!!.getAsJsonObject("data")

                        var post_id = data.asJsonObject["id"].asString
                        var post_title = data.asJsonObject["post_title"].asString
                        var post_content = data.asJsonObject["post_content"].asString
                        var post_image = data.asJsonObject["post_image"].asString
                        var anonymous = data.asJsonObject["anonymous"].asString.toBoolean()
                        var created_at = data.asJsonObject["created_at"].asString
                        var comment_count = ""

                        var user = data.asJsonObject["user"]

                        var user_id = user.asJsonObject["id"].asString
                        var user_username = user.asJsonObject["user_username"].asString
                        var user_displayname = user.asJsonObject["user_displayname"].asString
                        var user_email = user.asJsonObject["user_email"].asString
                        var user_image = user.asJsonObject["user_image"].asString

                        var userModel = User(
                            user_id,
                            user_username,
                            user_displayname,
                            user_email,
                            user_image
                        )

                        var postUpdateDetail = Post(
                            post_id,
                            post_title,
                            post_content,
                            post_image,
                            anonymous,
                            created_at,
                            userModel,
                            comment_count
                        )

                        _getPostUpdate.value = postUpdateDetail
                    }

                    _getPostUpdateError.value = "Success"

                    Log.e("Post Update Data", _getPostUpdateError.value.toString())
                } else {
                    _getPostUpdateError.value = response.message()
                }

                Log.d("Get Post Update Data", _getPostUpdateError.value.toString())
            } else {
                Log.e("Get Post Update Data Error", response.message())
            }
        }
    }

    val _postUpdate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val postUpdate: LiveData<String>
        get() = _postUpdate

    fun updatePost(
        post_image: MultipartBody.Part?,
        post_title: RequestBody,
        post_content: RequestBody,
        anonymous: Boolean,
        post_id: RequestBody
    ) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.updatePost(
                    post_image, post_title, post_content, anonymous, post_id
                ).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _postUpdate.value = "Discussion Successfully Updated"
                    } else {
                        _postUpdate.value = response.message()
                    }

                    Log.d("Update Discussion", response.message())
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun resetPostUpdate() {
        _postUpdate.value = ""
    }

    val _postDelete: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val postDelete: LiveData<String>
        get() = _postDelete

    fun deletePost(id: String) = viewModelScope.launch {
        repo.deletePost(id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _postDelete.value = "Discussion Successfully Deleted"
                } else {
                    _postDelete.value = response.message()
                }

                Log.d(
                    "Delete Post",
                    _postDelete.value.toString()
                )
            } else {
                _postDelete.value = response.message()
                Log.e("Delete Post", response.raw().message)
            }
        }
    }

    fun resetPostDelete() {
        _postDelete.value = ""
    }
}