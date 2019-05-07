package ru.lab.fintechcourseproject.database.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserOtherData(
    val region: String?,
    val tShirtSize: String?,
    val isClient: Boolean,
    val description: String?,
    val currentWork: String?,
    val resume: String?
) : Parcelable