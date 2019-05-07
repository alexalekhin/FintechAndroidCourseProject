package ru.lab.fintechcourseproject.network.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventType(
    @SerializedName("color")
    val color: String,
    @SerializedName("name")
    val typeName: String
): Parcelable
