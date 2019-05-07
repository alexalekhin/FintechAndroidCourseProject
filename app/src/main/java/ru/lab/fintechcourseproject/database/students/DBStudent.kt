package ru.lab.fintechcourseproject.database.students

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "students")
class DBStudent(
    @PrimaryKey
    @NonNull
    val id: Long,
    @NonNull
    val name: String,
    @NonNull
    val points: Double
) : Parcelable
