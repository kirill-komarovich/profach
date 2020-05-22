package com.profach.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.profach.entities.User

object AccountManager {
    val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser
    var currentUserProfile: User? = null
}