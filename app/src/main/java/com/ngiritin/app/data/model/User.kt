package com.ngiritin.app.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",

    @ServerTimestamp
    val createdAt: Date? = null,

    @ServerTimestamp
    val updatedAt: Date? = null
)