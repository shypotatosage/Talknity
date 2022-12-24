package com.imtuc.talknity.viewmodel

import android.content.Context
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
import com.imtuc.talknity.repository.AuthRepository
import com.imtuc.talknity.repository.ItemRepository
import com.imtuc.talknity.view.tmp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ItemRepository
): ViewModel() {
    val _communities: MutableLiveData<List<Community>> by lazy {
        MutableLiveData<List<Community>>()
    }

    val communities: LiveData<List<Community>>
        get() = _communities

    val _communityerror: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityerror: LiveData<String>
        get() = _communityerror

    fun getHomeCommunities()
            = viewModelScope.launch {
        repo.getCommunityHome().let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _communityerror.value = "Get Data Successful"
                    _communities.value = arrayListOf()

                    val arr: JsonArray = response!!.body()!!.getAsJsonArray("data")

                    var tmpArrList = arrayListOf<Community>()

                    for (item in arr) {
                        var community_id =  item.asJsonObject["id"].toString()
                        var community_name =  item.asJsonObject["community_name"].toString()
                        var community_description =  item.asJsonObject["community_description"].toString()
                        var community_contact =  item.asJsonObject["community_contact"].toString()
                        var community_logo =  item.asJsonObject["community_logo"].toString()
                        var created_at =  item.asJsonObject["created_at"].toString()

                        var user = item.asJsonObject["leader"]

                        var user_id =  user.asJsonObject["id"].toString()
                        var user_username =  user.asJsonObject["user_username"].toString()
                        var user_displayname =  user.asJsonObject["user_displayname"].toString()
                        var user_email =  user.asJsonObject["user_email"].toString()
                        var user_image =  user.asJsonObject["user_image"].toString()

                        var comcat = item.asJsonObject["category"]

                        var category_id =  comcat.asJsonObject["id"].toString()
                        var category_name =  comcat.asJsonObject["category_name"].toString()
                        var category_logo =  comcat.asJsonObject["category_logo"].toString()
                        var category_bg =  comcat.asJsonObject["category_bg"].toString()

                        var userModel = User(user_id, user_username, user_displayname, user_email, user_image)
                        var categoryModel = CommunityCategory(category_id, category_name, category_logo, category_bg)

                        var community = Community(community_id, community_name, community_description, community_contact, community_logo, created_at, categoryModel, userModel)

                        tmpArrList.add(community)
                    }

                    _communities.value = tmpArrList

                    Log.e("Community Data", _communities.value.toString())
                } else {
                    _communityerror.value = response.body()?.get("message")?.asString
                }

                Log.d("Get Community Home Data", _communityerror.value.toString())
            } else {
                Log.e("Get Community Home Data", response.body()?.get("message")!!.asString)
            }
        }
    }

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

    fun getHomePosts()
            = viewModelScope.launch {
        repo.getPostHome().let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _posterror.value = "Get Data Successful"
                    _posts.value = arrayListOf()

                    val arr: JsonArray = response!!.body()!!.getAsJsonArray("data")

                    var tmpArrList = arrayListOf<Post>()

                    for (item in arr) {
                        var post_id =  item.asJsonObject["id"].toString()
                        var post_title =  item.asJsonObject["post_title"].toString()
                        var post_content =  item.asJsonObject["post_content"].toString()
                        var post_image =  item.asJsonObject["post_image"].toString()
                        var anonymous =  item.asJsonObject["anonymous"].toString().toBoolean()
                        var created_at =  item.asJsonObject["created_at"].toString()

                        var user = item.asJsonObject["user"]

                        var user_id =  user.asJsonObject["id"].toString()
                        var user_username =  user.asJsonObject["user_username"].toString()
                        var user_displayname =  user.asJsonObject["user_displayname"].toString()
                        var user_email =  user.asJsonObject["user_email"].toString()
                        var user_image =  user.asJsonObject["user_image"].toString()

                        var userModel = User(user_id, user_username, user_displayname, user_email, user_image)

                        var post = Post(post_id, post_title, post_content, post_image, anonymous, created_at, userModel)

                        tmpArrList.add(post)
                    }

                    _posts.value = tmpArrList

                    Log.e("Community Data", _posts.value.toString())
                } else {
                    _posterror.value = response.body()?.get("message")?.asString
                }

                Log.d("Get Community Home Data", _posterror.value.toString())
            } else {
                Log.e("Get Community Home Data", response.body()?.get("message")!!.asString)
            }
        }
    }
}