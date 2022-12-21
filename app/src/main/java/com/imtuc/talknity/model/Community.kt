package com.imtuc.talknity.model

data class Community(
    var community_id: String,
    var community_name: String,
    var community_description: String,
    var community_contact: String,
    var community_logo: String,
    var community_category: String,
    var community_leader: String
) {
}