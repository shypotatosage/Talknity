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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

                    tmpArrList.add(Post("0", "Temp", "Temp", "Temp", true, "Temp", User("0", "Temp", "Temp", "Temp", "Temp")))

                    if (!response!!.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response!!.body()!!.getAsJsonArray("data")

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

                Log.d("Get Post Home Data", _posterror.value.toString())
            } else {
                Log.e("Get Post Home Data Error", response.message())
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

                    tmpArrList.add(Post("0", "Temp", "Temp", "Temp", true, "Temp", User("0", "Temp", "Temp", "Temp", "Temp")))

                    if (!response!!.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response!!.body()!!.getAsJsonArray("data")

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

                Log.d("Get Post Home Data", _posterror.value.toString())
            } else {
                Log.e("Get Post Home Data Error", response.message())
            }
        }
    }
}