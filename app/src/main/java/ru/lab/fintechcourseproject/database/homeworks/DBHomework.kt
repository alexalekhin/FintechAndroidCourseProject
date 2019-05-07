package ru.lab.fintechcourseproject.database.homeworks

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "homeworks")
class DBHomework(
    @PrimaryKey
    @NonNull
    val title: String,
    @NonNull
    val status: String,
    @NonNull
    val mark: String,
    @NonNull
    val markMax: String,
    val deadline: String?,
    @NonNull
    val lecId: Long
) : Parcelable
