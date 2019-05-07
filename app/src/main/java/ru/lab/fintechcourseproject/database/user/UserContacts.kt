package ru.lab.fintechcourseproject.database.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserContacts(
    val email: String?,
    val phone: String?,
    val skype: String?
): Parcelable