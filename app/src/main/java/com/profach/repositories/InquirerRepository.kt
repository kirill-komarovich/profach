package com.profach.repositories

import com.google.firebase.firestore.Query
import com.profach.entities.Inquirer
import kotlinx.coroutines.tasks.await

class InquirerRepository : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)

    fun buildListQuery(userIds: List<String?> = emptyList()): Query {
        return collection.whereIn("userId", userIds).orderBy("createdAt", Query.Direction.DESCENDING)
    }

    suspend fun save(inquirer: Inquirer) {
        collection.add(inquirer).await()
    }

    companion object {
        private const val COLLECTION_NAME = "inquirers"
    }
}