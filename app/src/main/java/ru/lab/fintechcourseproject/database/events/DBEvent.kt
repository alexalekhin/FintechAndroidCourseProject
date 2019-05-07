package ru.lab.fintechcourseproject.database.events

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "events")
class DBEvent(
    @PrimaryKey
    val title: String,
    val typeName: String?,
    val color: String?,
    val date: String,
    val status: String
) : Parcelable
