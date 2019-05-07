package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PerformancePerson(val name: String, val points: Int) : Parcelable
