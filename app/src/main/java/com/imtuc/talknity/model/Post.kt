package com.imtuc.talknity.model

data class Post(
    var post_id: String,
    var post_title: String,
    var post_content: String,
    var post_image: String,
    var anonymous: Boolean,
    var creator: User
) {

}
