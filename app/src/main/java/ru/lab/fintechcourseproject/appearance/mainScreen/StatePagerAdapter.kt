package ru.lab.fintechcourseproject.appearance.mainScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * Класс, предоставляющий адаптер для заполнения страниц внутри ViewPager.
 * Использует фрагменты для управления каждой страницей.
 */
class StatePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    internal fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(i: Int) = fragmentList[i]

    override fun getCount() = fragmentList.size
}
