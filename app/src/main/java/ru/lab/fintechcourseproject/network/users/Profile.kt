package ru.lab.fintechcourseproject.network.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Profile(
    @Expose
    @SerializedName("user")
    val user: User,
    @Expose
    @SerializedName("status")
    val status: String
)
