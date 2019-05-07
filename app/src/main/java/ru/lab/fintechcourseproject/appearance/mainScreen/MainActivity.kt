package ru.lab.fintechcourseproject.appearance.mainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lab.fintechcourseproject.R


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main_container, FlowFragment.newInstance(), FlowFragment.TAG)
            .addToBackStack(FlowFragment.TAG)
            .commit()
        supportActionBar?.setTitle(R.string.title_events)
    }


    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 2 -> supportFragmentManager.popBackStackImmediate()
            else -> supportFinishAfterTransition()
        }
    }
}
