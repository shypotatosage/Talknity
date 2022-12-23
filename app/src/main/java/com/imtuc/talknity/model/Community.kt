package com.imtuc.talknity.model

import java.sql.Timestamp

data class Community(
    var community_id: String,
    var community_name: String,
    var community_description: String,
    var community_contact: String,
    var community_logo: String,
    var created_at: String,
    var community_category: CommunityCategory,
    var leader: User
) {
}