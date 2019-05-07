package ru.lab.fintechcourseproject.appearance.mainScreen

import androidx.appcompat.app.ActionBar

interface OnFragmentInteractionListener {
    fun onBackPressed()
    fun getSupportActionBar(): ActionBar?
}