package ru.lab.fintechcourseproject.database.lectures

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "lectures")
class DBLecture(
    @PrimaryKey
    @NonNull
    val id: Long,
    @NonNull
    val lecTitle: String
) : Parcelable
