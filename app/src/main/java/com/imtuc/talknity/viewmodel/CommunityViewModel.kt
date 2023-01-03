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
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel() {
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

    fun getOwnedCommunities(uid: String) = viewModelScope.launch {
        repo.getOwnedCommunities(uid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _ownedCommunitiesError.value = "Get Data Successful"
                    _ownedCommunities.value = arrayListOf()

                    var tmpArrList = arrayListOf<Community>()

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
                            var count = item.asJsonObject["member_count"].asString

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
                                userModel,
                                count
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
                Log.e("Get Communities Data", response.raw().message)
            }
        }
    }

    val _categoryCommunities: MutableLiveData<List<Community>> by lazy {
        MutableLiveData<List<Community>>()
    }

    val categoryCommunities: LiveData<List<Community>>
        get() = _categoryCommunities

    val _categoryCommunitiesError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val categoryCommunitiesError: LiveData<String>
        get() = _categoryCommunitiesError

    fun getCommunitiesCategory(cid: String) = viewModelScope.launch {
        repo.getCommunitiesCategory(cid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryCommunitiesError.value = "Get Data Successful"
                    _categoryCommunities.value = arrayListOf()

                    var tmpArrList = arrayListOf<Community>()

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
                            var count = item.asJsonObject["member_count"].asString

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
                                userModel,
                                count
                            )

                            tmpArrList.add(community)
                        }
                    }

                    _categoryCommunities.value = tmpArrList
                } else {
                    _categoryCommunitiesError.value = response.message()
                }

                Log.d(
                    "Get Communities Based On Category Data",
                    _categoryCommunitiesError.value.toString()
                )
            } else {
                Log.e("Get Communities Based On Category Data", response.raw().message)
            }
        }
    }

    fun searchCommunitiesCategory(cid: String, search_key: String) = viewModelScope.launch {
        repo.searchCommunitiesCategory(cid, search_key).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryCommunitiesError.value = "Get Data Successful"
                    _categoryCommunities.value = arrayListOf()

                    var tmpArrList = arrayListOf<Community>()

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
                            var count = item.asJsonObject["member_count"].asString

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
                                userModel,
                                count
                            )

                            tmpArrList.add(community)
                        }
                    }

                    _categoryCommunities.value = tmpArrList
                } else {
                    _categoryCommunitiesError.value = response.message()
                }

                Log.d(
                    "Search Communities Based On Category Data",
                    _categoryCommunitiesError.value.toString()
                )
            } else {
                Log.e("Search Communities Based On Category Data", response.raw().message)
            }
        }
    }

    val _communitycreate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communitycreate: LiveData<String>
        get() = _communitycreate

    fun createCommunity(
        community_logo: MultipartBody.Part,
        community_name: RequestBody,
        community_description: RequestBody,
        category_id: RequestBody,
        community_contact: RequestBody,
        leader_id: RequestBody
    ) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.createCommunity(
                    community_logo,
                    community_name,
                    community_description,
                    community_contact,
                    category_id,
                    leader_id
                ).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _communitycreate.value = "Community Successfully Created"
                    } else {
                        _communitycreate.value =
                            "Failed To Create Community\n" + response.raw().message
                    }

                    Log.d("Create Post Image", _communitycreate.value!!)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun resetCommunityCreate() {
        _communitycreate.value = ""
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

    fun getCategory() = viewModelScope.launch {
        repo.getCommunityCategories().let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryError.value = "Get Data Successful"
                    _category.value = arrayListOf()

                    var tmpArrList = arrayListOf<CommunityCategory>()

                    tmpArrList.add(
                        CommunityCategory(
                            "0",
                            "All Category",
                            "images/category/category_icon_183800.png",
                            "FF7800",
                            "FBB372",
                            "F7F7F7"
                        )
                    )

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

    fun searchCategory(key: String) = viewModelScope.launch {
        repo.searchCommunityCategories(key).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _categoryError.value = "Get Data Successful"
                    _category.value = arrayListOf()

                    var tmpArrList = arrayListOf<CommunityCategory>()

                    tmpArrList.add(
                        CommunityCategory(
                            "0",
                            "All Category",
                            "images/category/category_icon_183800.png",
                            "FF7800",
                            "FBB372",
                            "F7F7F7"
                        )
                    )

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
                Log.e("Search Category Data", response.raw().message)
            }
        }
    }

    val _communityMembersError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityMembersError: LiveData<String>
        get() = _communityMembersError

    val _communityMembers: MutableLiveData<CommunityMembers> by lazy {
        MutableLiveData<CommunityMembers>()
    }

    val communityMembers: LiveData<CommunityMembers>
        get() = _communityMembers

    fun getCommunityMembers(cid: String) = viewModelScope.launch {
        repo.getCommunityMembers(cid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val item: JsonObject = response.body()!!.getAsJsonObject("data")

                        var community_id = item.asJsonObject["id"].asString
                        var community_name = item.asJsonObject["community_name"].asString
                        var community_description =
                            item.asJsonObject["community_description"].asString
                        var community_contact = item.asJsonObject["community_contact"].asString
                        var community_logo = item.asJsonObject["community_logo"].asString
                        var created_at = item.asJsonObject["created_at"].asString
                        var count = item.asJsonObject["member_count"].asString

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

                        var tmpArrList = arrayListOf<CommunityMember>()
                        tmpArrList.clear()

                        if (!item.get("members").isJsonNull) {
                            var commembers = item.getAsJsonArray("members")

                            for (item in commembers) {
                                var member_id = item.asJsonObject["id"].asString
                                var com_id = item.asJsonObject["community_id"].asString

                                var usr = item.asJsonObject["user"]

                                var usr_id = usr.asJsonObject["id"].asString
                                var usr_username = usr.asJsonObject["user_username"].asString
                                var usr_displayname = usr.asJsonObject["user_displayname"].asString
                                var usr_email = usr.asJsonObject["user_email"].asString
                                var usr_image = usr.asJsonObject["user_image"].asString

                                var usrMember = User(
                                    usr_id,
                                    usr_username,
                                    usr_displayname,
                                    usr_email,
                                    usr_image
                                )

                                var comMemb = CommunityMember(
                                    member_id,
                                    com_id,
                                    usrMember
                                )

                                tmpArrList.add(comMemb)
                            }
                        }

                        var communitymemb = CommunityMembers(
                            community_id,
                            community_name,
                            community_description,
                            community_contact,
                            community_logo,
                            created_at,
                            categoryModel,
                            userModel,
                            count,
                            tmpArrList
                        )

                        _communityMembers.value = communitymemb
                    }

                    _communityMembersError.value = "Get Data Successful"
                } else {
                    _communityMembersError.value = response.message()
                }

                Log.d(
                    "Get Community Members Data",
                    _communityMembersError.value.toString()
                )
            } else {
                _communityMembersError.value = response.message()
                Log.e("Get Community Members Data", response.raw().message)
            }
        }
    }

    val _communityMemberRemove: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityMemberRemove: LiveData<String>
        get() = _communityMemberRemove

    fun removeMember(id: String) = viewModelScope.launch {
        repo.removeMember(id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _communityMemberRemove.value = "Member Successfully Removed"
                } else {
                    _communityMemberRemove.value = response.message()
                }

                Log.d(
                    "Remove Community Member",
                    _communityMemberRemove.value.toString()
                )
            } else {
                _communityMemberRemove.value = response.message()
                Log.e("Remove Community Member", response.raw().message)
            }
        }
    }

    fun resetMemberRemove() {
        _communityMemberRemove.value = ""
    }

    val _communityMemberJoin: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityMemberJoin: LiveData<String>
        get() = _communityMemberJoin

    fun joinCommunity(cid: String, uid: String) = viewModelScope.launch {
        repo.joinCommunity(cid, uid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _communityMemberJoin.value = "Successfully Joined The Community"
                } else {
                    _communityMemberJoin.value = response.message()
                }

                Log.d(
                    "Join Community Member",
                    _communityMemberJoin.value.toString()
                )
            } else {
                _communityMemberJoin.value = response.message()
                Log.e("Join Community Member", response.raw().message)
            }
        }
    }

    fun resetMemberJoin() {
        _communityMemberJoin.value = ""
    }

    val _getCommunityUpdateError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getCommunityUpdateError: LiveData<String>
        get() = _getCommunityUpdateError

    val _getCommunityUpdate: MutableLiveData<Community> by lazy {
        MutableLiveData<Community>()
    }

    val getCommunityUpdate: LiveData<Community>
        get() = _getCommunityUpdate

    fun getCommunityUpdate(cid: String) = viewModelScope.launch {
        repo.getCommunityMembers(cid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val item: JsonObject = response.body()!!.getAsJsonObject("data")

                        var community_id = item.asJsonObject["id"].asString
                        var community_name = item.asJsonObject["community_name"].asString
                        var community_description =
                            item.asJsonObject["community_description"].asString
                        var community_contact = item.asJsonObject["community_contact"].asString
                        var community_logo = item.asJsonObject["community_logo"].asString
                        var created_at = item.asJsonObject["created_at"].asString
                        var count = item.asJsonObject["member_count"].asString

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

                        var communitymemb = Community(
                            community_id,
                            community_name,
                            community_description,
                            community_contact,
                            community_logo,
                            created_at,
                            categoryModel,
                            userModel,
                            count
                        )

                        _getCommunityUpdate.value = communitymemb
                    }

                    _getCommunityUpdateError.value = "Success"
                } else {
                    _getCommunityUpdateError.value = response.message()
                }

                Log.d(
                    "Get Community Members Data",
                    _getCommunityUpdateError.value.toString()
                )
            } else {
                _getCommunityUpdateError.value = response.message()
                Log.e("Get Community Members Data", response.raw().message)
            }
        }
    }

    val _communityUpdate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityUpdate: LiveData<String>
        get() = _communityUpdate

    fun updateCommunity(
        community_logo: MultipartBody.Part?,
        community_name: RequestBody,
        community_description: RequestBody,
        category_id: RequestBody,
        community_contact: RequestBody,
        community_id: RequestBody
    ) {
        val service = AppModule.getRetrofitServiceInstance(AppModule.getRetrofitInstance())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.updateCommunity(
                    community_logo, community_name, community_description, community_contact, category_id, community_id
                ).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _communityUpdate.value = "Community Successfully Updated"
                    } else {
                        _communityUpdate.value = response.message()
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

    fun resetCommunityUpdate() {
        _communityUpdate.value = ""
    }

    val _communityDelete: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityDelete: LiveData<String>
        get() = _communityDelete

    fun deleteCommunity(id: String) = viewModelScope.launch {
        repo.deleteCommunity(id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _communityDelete.value = "Community Successfully Deleted"
                } else {
                    _communityDelete.value = response.message()
                }

                Log.d(
                    "Delete Community",
                    _communityDelete.value.toString()
                )
            } else {
                _communityDelete.value = response.message()
                Log.e("Delete Community", response.raw().message)
            }
        }
    }

    fun resetPostDelete() {
        _communityDelete.value = ""
    }

    val _communityMemberOut: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val communityMemberOut: LiveData<String>
        get() = _communityMemberOut

    fun signoutCommunity(cid: String, uid: String) = viewModelScope.launch {
        repo.signoutCommunity(cid, uid).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _communityMemberOut.value = "Successfully Signed Out From The Community"
                } else {
                    _communityMemberOut.value = response.message()
                }

                Log.d(
                    "Signout Community Member",
                    _communityMemberOut.value.toString()
                )
            } else {
                _communityMemberOut.value = response.message()
                Log.e("Signout Community Member", response.raw().message)
            }
        }
    }

    fun resetMemberOut() {
        _communityMemberOut.value = ""
    }

}