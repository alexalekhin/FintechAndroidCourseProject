package ru.lab.fintechcourseproject.network.courses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CourseAbout(
    @SerializedName("html")
    val html: String
) : Parcelable
