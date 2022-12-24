package com.imtuc.talknity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.imtuc.talknity.model.Community
import com.imtuc.talknity.model.CommunityCategory
import com.imtuc.talknity.model.User
import com.imtuc.talknity.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repo: ItemRepository
): ViewModel() {
    val _ownedCommunities: MutableLiveData<List<Community>> by lazy {
        MutableLiveData<List<Community>>()
    }

    val ownedCommunities: LiveData<List<Community>>
        get() = _ownedCommunities

    val _ownedCommunitiesError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val ownedCommunitiesError: LiveData<String>
        get() = _ownedCommunitiesError

    fun getOwnedCommunities(uid: String)
            = viewModelScope.launch {
        repo.getOwnedCommunities(uid).let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _ownedCommunitiesError.value = "Get Data Successful"
                    _ownedCommunities.value = arrayListOf()

                    var tmpArrList = arrayListOf<Community>()

                    tmpArrList.add(Community("0", "Temp", "Temp", "Temp", "Temp", "Temp", CommunityCategory("0", "All Category", "images/category/category_icon_183800.png", "FF7800", "FBB372", "F7F7F7"), User("0", "Temp", "Temp", "Temp", "Temp")))

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var community_id = item.asJsonObject["id"].asString
                            var community_name = item.asJsonObject["community_name"].asString
                            var community_description =
                                item.asJsonObject["community_description"].asString
                            var community_contact = item.asJsonObject["community_contact"].asString
                            var community_logo = item.asJsonObject["community_logo"].asString
                            var created_at = item.asJsonObject["created_at"].asString

                            var user = item.asJsonObject["leader"]

                            var user_id = user.asJsonObject["id"].asString
                            var user_username = user.asJsonObject["user_username"].asString
                            var user_displayname = user.asJsonObject["user_displayname"].asString
                            var user_email = user.asJsonObject["user_email"].asString
                            var user_image = user.asJsonObject["user_image"].asString

                            var comcat = item.asJsonObject["category"]

                            var category_id = comcat.asJsonObject["id"].asString
                            var category_name = comcat.asJsonObject["category_name"].asString
                            var category_logo = comcat.asJsonObject["category_logo"].asString
                            var category_color1 = comcat.asJsonObject["category_color1"].asString
                            var category_color2 = comcat.asJsonObject["category_color2"].asString
                            var category_color3 = comcat.asJsonObject["category_color3"].asString

                            var userModel = User(
                                user_id,
                                user_username,
                                user_displayname,
                                user_email,
                                user_image
                            )
                            var categoryModel = CommunityCategory(
                                category_id,
                                category_name,
                                category_logo,
                                category_color1,
                                category_color2,
                                category_color3
                            )

                            var community = Community(
                                community_id,
                                community_name,
                                community_description,
                                community_contact,
                                community_logo,
                                created_at,
                                categoryModel,
                                userModel
                            )

                            tmpArrList.add(community)
                        }
                    }

                    _ownedCommunities.value = tmpArrList
                } else {
                    _ownedCommunitiesError.value = response.message()
                }

                Log.d("Get Communities Data", _ownedCommunitiesError.value.toString())
            } else {
                Log.e("Get Communities Data",  response.raw().message)
            }
        }
    }

    val _category: MutableLiveData<List<CommunityCategory>> by lazy {
        MutableLiveData<List<CommunityCategory>>()
    }

    val category: LiveData<List<CommunityCategory>>
        get() = _category

    val _categoryError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val categoryError: LiveData<String>
        get() = _categoryError

    fun getCategory()
            = viewModelScope.launch {
        repo.getCommunityCategories().let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryError.value = "Get Data Successful"
                    _category.value = arrayListOf()

                    var tmpArrList = arrayListOf<CommunityCategory>()

                    tmpArrList.add(CommunityCategory("0", "All Category", "images/category/category_icon_183800.png", "FF7800", "FBB372", "F7F7F7"))

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var category_id = item.asJsonObject["id"].asString
                            var category_name = item.asJsonObject["category_name"].asString
                            var category_logo = item.asJsonObject["category_logo"].asString
                            var category_color1 = item.asJsonObject["category_color1"].asString
                            var category_color2 = item.asJsonObject["category_color2"].asString
                            var category_color3 = item.asJsonObject["category_color3"].asString

                            var category = CommunityCategory(
                                category_id,
                                category_name,
                                category_logo,
                                category_color1,
                                category_color2,
                                category_color3
                            )

                            tmpArrList.add(category)
                        }
                    }

                    _category.value = tmpArrList
                } else {
                    _categoryError.value = response.message()
                }

                Log.d("Get Category Data", _categoryError.value.toString())
            } else {
                Log.e("Get Category Data", response.raw().message)
            }
        }
    }

    fun searchCategory(key: String)
            = viewModelScope.launch {
        repo.searchCommunityCategories(key).let {
                response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryError.value = "Get Data Successful"
                    _category.value = arrayListOf()

                    var tmpArrList = arrayListOf<CommunityCategory>()

                    tmpArrList.add(CommunityCategory("0", "All Category", "images/category/category_icon_183800.png", "FF7800", "FBB372", "F7F7F7"))

                    if (!response!!.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response!!.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var category_id = item.asJsonObject["id"].asString
                            var category_name = item.asJsonObject["category_name"].asString
                            var category_logo = item.asJsonObject["category_logo"].asString
                            var category_color1 = item.asJsonObject["category_color1"].asString
                            var category_color2 = item.asJsonObject["category_color2"].asString
                            var category_color3 = item.asJsonObject["category_color3"].asString

                            var category = CommunityCategory(
                                category_id,
                                category_name,
                                category_logo,
                                category_color1,
                                category_color2,
                                category_color3
                            )

                            tmpArrList.add(category)
                        }
                    }

                    _category.value = tmpArrList
                } else {
                    _categoryError.value = response.message()
                }

                Log.d("Search Category Data", _categoryError.value.toString())
            } else {
                Log.e("Search Category Data",  response.raw().message)
            }
        }
    }

}