package com.profach.repositories

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.profach.entities.User
import kotlinx.coroutines.tasks.await

class UserRepository : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)

    suspend fun findProfile(userId: String): User? {
        val result = collection.document(userId).get().await()

        return result.toObject(User::class.java)
    }

    fun buildStudentsListQuery(userId: String): Query {
        return collection.document(userId).collection(STUDENTS_COLLECTION_NAME).limit(PER_PAGE.toLong())
    }

    suspend fun addStudent(userId: String, email: String, group: String) {
        try {
            val studentSnapshot = collection.whereEqualTo("email", email).get().await()
            val studentId = studentSnapshot.documents[0].id
            val data = hashMapOf(
                "email" to studentSnapshot.documents[0].data!!["email"] as String,
                "displayName" to studentSnapshot.documents[0].data!!["displayName"] as String,
                "group" to group
            )
            collection.document(userId).collection(STUDENTS_COLLECTION_NAME).document(studentId).set(data).await()
        } catch (e: Exception) {
            Log.e("UserRepository", "", e)
        }
    }

    suspend fun removeStudent(userId: String, id: String) {
        try {
            collection.document(userId).collection(STUDENTS_COLLECTION_NAME).document(id).delete().await()
        } catch (e: Exception) {
            Log.e("UserRepository", "", e)
        }
    }

    suspend fun updateUserRole(userId: String, role: User.Role) {
        collection.document(userId).set(hashMapOf("role" to role), SetOptions.merge()).await()
    }

    companion object {
        private const val COLLECTION_NAME = "users"
        private const val STUDENTS_COLLECTION_NAME = "students"
        private const val PER_PAGE = 10
    }
}