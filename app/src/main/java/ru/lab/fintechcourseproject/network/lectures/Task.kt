package ru.lab.fintechcourseproject.network.lectures

import com.google.gson.annotations.SerializedName


data class Task(
    @SerializedName("title")
    val title: String,
    @SerializedName("max_score")
    val maxScore: String,
    @SerializedName("deadline_date")
    val deadline: String?,
    @SerializedName("task_type")
    val taskType: String
)
