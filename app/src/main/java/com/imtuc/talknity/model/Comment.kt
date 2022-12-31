package com.imtuc.talknity.model

import org.w3c.dom.Text
import java.sql.Time

data class Comment(
    var id: String,
    var content: String,
    var created_at: String,
    var user: User
    ) {
}