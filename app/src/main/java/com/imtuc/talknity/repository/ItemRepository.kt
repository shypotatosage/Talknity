package com.imtuc.talknity.repository

import com.imtuc.talknity.retrofit.EndPointAPI
import javax.inject.Inject

class ItemRepository@Inject constructor(
    private val api: EndPointAPI
) {

    suspend fun getCommunityHome()
            = api.getCommunityHome()

    suspend fun getPostHome()
            = api.getPostHome()

}