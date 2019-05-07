package ru.lab.fintechcourseproject.misc

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import ru.lab.fintechcourseproject.appearance.performanceScreen.PerformanceDetailsFragment

class RecycleViewDataManipulator(private val supportFragmentManager: FragmentManager) :
    SearchView.OnQueryTextListener {
    private var isSortedByName = false
    private var isSortedByPoints = false

    fun sortByName() {
        val fragment = supportFragmentManager.findFragmentByTag(PerformanceDetailsFragment.TAG)
        isSortedByName = !isSortedByName
        if (fragment is PerformanceDetailsFragment)
            fragment.studentsAdapter.sortElementsByName(isSortedByName)
    }

    fun sortByPoints() {
        val fragment = supportFragmentManager.findFragmentByTag(PerformanceDetailsFragment.TAG)
        isSortedByPoints = !isSortedByPoints
        if (fragment is PerformanceDetailsFragment)
            fragment.studentsAdapter.sortElementsByPoints(isSortedByPoints)
    }


    override fun onQueryTextChange(text: String?): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(PerformanceDetailsFragment.TAG)
        if (fragment is PerformanceDetailsFragment)
            fragment.studentsAdapter.filter(text!!)
        return false
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(PerformanceDetailsFragment.TAG)
        if (fragment is PerformanceDetailsFragment)
            fragment.studentsAdapter.filter(text!!)
        return false
    }

    fun resetSorting() {
        isSortedByName = false
        isSortedByPoints = false
    }
}
