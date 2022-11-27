package com.imtuc.talknity.model

import org.w3c.dom.Text
import java.sql.Time

data class Comment(
    var id: Int,
    var content: Text,
    var time: Time,
    var user_id: Int,
    var post_id: Int,
    ) {
}