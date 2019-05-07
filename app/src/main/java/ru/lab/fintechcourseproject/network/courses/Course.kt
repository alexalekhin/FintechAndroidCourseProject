package ru.lab.fintechcourseproject.network.courses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Контракт класс для хранения данных о курсах пользователя
 */
@Parcelize
data class Course(
    @SerializedName("title")
    val title: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("event_date_start")
    val date: String,
    @SerializedName("url")
    val url: String
) : Parcelable
