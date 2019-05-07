package ru.lab.fintechcourseproject.database.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserMainData(
    val lastName: String?,
    val firstName: String?,
    val patronymic: String?,
    val avatarUrl: String?,
    val bDay: String?
): Parcelable