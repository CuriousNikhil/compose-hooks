package me.nikhilchaudhari.usefetch.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

@JsonClass(generateAdapter = true)
data class UsersList(
    val data: List<User>
)
