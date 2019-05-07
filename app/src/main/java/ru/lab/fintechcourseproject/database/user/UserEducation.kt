package ru.lab.fintechcourseproject.database.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEducation(
    val school: String?,
    val schoolGradYear: Long?,
    val university: String?,
    val universityGrad: Long?,
    val faculty: String?,
    val grade: String?,
    val department: String?
): Parcelable