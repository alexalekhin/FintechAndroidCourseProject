package ru.lab.fintechcourseproject.appearance.mainScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.CoursesFragment
import ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab.EventsFragment
import ru.lab.fintechcourseproject.appearance.mainScreen.profileTab.UserProfileFragment

class FlowFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewPager: ViewPager
    private var statePagerAdapter: StatePagerAdapter? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private var prevMenuItem: MenuItem? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                viewPager.currentItem = 0
                listener?.getSupportActionBar()?.setTitle(R.string.title_events)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_courses -> {
                viewPager.currentItem = 1
                listener?.getSupportActionBar()?.setTitle(R.string.title_courses)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                viewPager.currentItem = 2
                listener?.getSupportActionBar()?.setTitle(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow, container, false)
        this.bottomNavigationView = view.findViewById(R.id.navigation)
        this.viewPager = view.findViewById(R.id.main_container)
        this.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setupViewPager(this.viewPager)
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bottomNavigationView.menu.getItem(0)?.isChecked = false
                }
                bottomNavigationView.menu.getItem(position)?.isChecked = true
                listener?.getSupportActionBar()?.title = bottomNavigationView.menu.getItem(position)?.title
                prevMenuItem = bottomNavigationView.menu.getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val adapter = StatePagerAdapter(childFragmentManager).apply {
            addFragment(EventsFragment.newInstance(), "EventsFragment")
            addFragment(CoursesFragment.newInstance(), "CoursesFragment")
            addFragment(UserProfileFragment.newInstance(), "UserProfileFragment")
        }
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = MAX_PAGES_LOADED
        this.statePagerAdapter = adapter
        viewPager.currentItem = 1 //Мои курсы

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance() = FlowFragment()

        private const val MAX_PAGES_LOADED: Int = 4
        const val TAG = "FlowFragment"
    }
}
