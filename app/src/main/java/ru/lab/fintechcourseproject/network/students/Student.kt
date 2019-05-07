package ru.lab.fintechcourseproject.network.students

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    @SerializedName("student")
    val name: String,
    @SerializedName("student_id")
    val id: Long,
    @SerializedName("grades")
    val grades: ArrayList<Grade>
):Parcelable
