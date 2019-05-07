package ru.lab.fintechcourseproject.appearance.performanceScreen

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.misc.RecycleViewDataManipulator


class PerformanceDetailsActivity : AppCompatActivity(),
    OnProgressFragmentInteractionListener {

    private lateinit var recycleViewDataManipulator: RecycleViewDataManipulator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recycleViewDataManipulator = RecycleViewDataManipulator(supportFragmentManager)
        setContentView(R.layout.activity_academic_progress_details)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_contacts_container,
                PerformanceDetailsFragment.newInstance(),
                PerformanceDetailsFragment.TAG
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(recycleViewDataManipulator)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sort -> recycleViewDataManipulator.sortByPoints()
            R.id.sort_alpha -> recycleViewDataManipulator.sortByName()
            else -> return false
        }
        return true
    }

    override fun resetSorting() {
        recycleViewDataManipulator.resetSorting()
    }
}
