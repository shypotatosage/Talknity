package com.imtuc.talknity.model

data class CommunityMembers(
    var community_id: String,
    var community_name: String,
    var community_description: String,
    var community_contact: String,
    var community_logo: String,
    var created_at: String,
    var community_category: CommunityCategory,
    var leader: User,
    var member_count: String,
    var members: ArrayList<CommunityMember>
) {
}