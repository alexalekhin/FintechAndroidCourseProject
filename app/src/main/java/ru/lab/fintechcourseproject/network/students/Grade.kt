package ru.lab.fintechcourseproject.network.students

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Grade(
    @SerializedName("mark")
    val mark: Double
):Parcelable
