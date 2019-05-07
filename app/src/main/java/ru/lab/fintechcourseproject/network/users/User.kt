package ru.lab.fintechcourseproject.network.users

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("middle_name")
    var patronymic: String?,
    @SerializedName("avatar")
    var avatarUrl: String?,
    @SerializedName("birthday")
    var bDay: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("phone_mobile")
    var phone: String?,
    @SerializedName("t_shirt_size")
    var tShirtSize: String?,
    @SerializedName("is_client")
    var isClient: Boolean,
    @SerializedName("skype_login")
    var skype: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("school")
    var school: String?,
    @SerializedName("school_graduation")
    var schoolGradYear: Long?,
    @SerializedName("university")
    var university: String?,
    @SerializedName("university_graduation")
    var universityGrad: Long?,
    @SerializedName("faculty")
    var faculty: String?,
    @SerializedName("grade")
    var grade: String?,
    @SerializedName("department")
    var department: String?,
    @SerializedName("current_work")
    var currentWork: String?,
    @SerializedName("resume")
    var resume: String?
) : Parcelable
