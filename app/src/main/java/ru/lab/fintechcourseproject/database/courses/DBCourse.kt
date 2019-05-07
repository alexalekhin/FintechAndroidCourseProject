package ru.lab.fintechcourseproject.database.courses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "courses")
data class DBCourse(
    @PrimaryKey
    val title: String,
    val status: String,
    val date: String,
    val url: String
) : Parcelable
