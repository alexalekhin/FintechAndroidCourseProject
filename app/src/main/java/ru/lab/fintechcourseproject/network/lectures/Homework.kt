package ru.lab.fintechcourseproject.network.lectures

import com.google.gson.annotations.SerializedName


data class Homework(
    @SerializedName("status")
    val status: String,
    @SerializedName("mark")
    val mark: String,
    @SerializedName("task")
    val task: Task
)
