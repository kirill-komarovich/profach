package com.profach.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class Inquirer(
    @DocumentId
    var id: String? = null,
    var type: Type? = null,
    var userId: String? = null,
    var answers: List<Int>? = null,
    @ServerTimestamp
    var createdAt: Timestamp? = null
) {

    companion object {
        fun supported() : List<Inquirer> {
            return Type.values().map { Inquirer(type = it) }
        }
    }

    enum class Type {
        YOVAYSHI,
        KLIMOV,
        HOLOMSTOCK,
        HOLLAND;

        fun toLowerCase(): String {
            return name.toLowerCase()
        }
    }
}
