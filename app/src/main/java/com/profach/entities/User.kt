package com.profach.entities

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var id: String? = null,
    var email: String? = null,
    var displayName: String? = null,
    var group: String? = null,
    var role: Role? = null
) {
    enum class Role {
        TEACHER
    }
}