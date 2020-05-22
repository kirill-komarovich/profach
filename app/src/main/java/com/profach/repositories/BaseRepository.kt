package com.profach.repositories

import com.google.firebase.firestore.FirebaseFirestore

interface BaseRepository {
    val database: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()
}