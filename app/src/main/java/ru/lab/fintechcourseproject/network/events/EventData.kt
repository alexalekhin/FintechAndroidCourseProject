package ru.lab.fintechcourseproject.network.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Контракт класс для хранения данных о событиях
 */
@Parcelize
data class EventData(
    @SerializedName("title")
    val title: String,
    @SerializedName("event_type")
    val type: EventType?,
    @SerializedName("date_start")
    val date: String
) : Parcelable
