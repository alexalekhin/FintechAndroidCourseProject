package ru.lab.fintechcourseproject.network.lectures

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Lecture(
    @Expose
    @SerializedName("id")
    val id: Long,
    @Expose
    @SerializedName("title")
    val lecTitle: String,
    @SerializedName("tasks")
    val tasks: ArrayList<Homework>
)
