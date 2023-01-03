package com.imtuc.talknity.model

data class PostComment(
    var post_id: String,
    var post_title: String,
    var post_content: String,
    var post_image: String,
    var anonymous: Boolean,
    var created_at: String,
    var creator: User,
    var comments: ArrayList<Comment>
) {

}
