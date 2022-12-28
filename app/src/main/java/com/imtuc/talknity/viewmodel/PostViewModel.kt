package com.imtuc.talknity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.model.Post
import com.imtuc.talknity.model.User
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
): ViewModel() {
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

    fun getPosts()
            = viewModelScope.launch {
        repo.getPost().let {
                response ->
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
                                userModel
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

    fun getOwnedPosts(uid: String)
            = viewModelScope.launch {
        repo.getOwnedPosts(uid).let {
                response ->
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
                                userModel
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

    fun searchPosts(key: String)
            = viewModelScope.launch {
        repo.searchPosts(key).let {
                response ->
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
                                userModel
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

    fun createPost(post_title: String, post_content: String, anonymous: Boolean, user_id: String)
            = viewModelScope.launch {
        repo.createPost(post_title, post_content, anonymous, user_id).let {
                response ->
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

    fun createPostImage(post_image: MultipartBody.Part, post_title: RequestBody, post_content: RequestBody, anonymous: Boolean, user_id: RequestBody) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.createPostImage(post_image, post_title, post_content, anonymous, user_id).execute()
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
}