package ru.lab.fintechcourseproject.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginInfo(val email: String, val password: String) : Parcelable
