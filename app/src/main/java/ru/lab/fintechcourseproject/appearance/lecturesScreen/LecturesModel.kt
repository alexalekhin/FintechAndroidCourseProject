package ru.lab.fintechcourseproject.appearance.lecturesScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.database.homeworks.DBHomework
import ru.lab.fintechcourseproject.database.lectures.DBLecture
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.lectures.HomeworksContainer
import ru.lab.fintechcourseproject.network.lectures.convertLecture
import ru.lab.fintechcourseproject.network.lectures.getHomeworksOfLecture

class LecturesModel(
    private val fintechService: NetworkService,
    private val database: AppDb,
    private val context: Context
) : Model {

    private var mutableLectures: MutableLiveData<List<DBLecture>> = MutableLiveData()
    private var mutableLecturesState: MutableLiveData<LecturesState> = MutableLiveData()
    val lectures: LiveData<List<DBLecture>> = mutableLectures
    val lecturesState: LiveData<LecturesState> = mutableLecturesState

    fun loadLectures(source: LecturesPresenter.DataSource = LecturesPresenter.DataSource.UNSPECIFIED) {
        mutableLecturesState.value = LecturesState.IN_PROCESS
        when (source) {
            LecturesPresenter.DataSource.NETWORK -> updateHomeworkData()
            LecturesPresenter.DataSource.MEMORY -> mutableLectures.value = database.getLectureDao().getLectures()
            LecturesPresenter.DataSource.UNSPECIFIED -> {
                val lecturesFromMemory = database.getLectureDao().getLectures()
                if (lecturesFromMemory.isEmpty()) {
                    updateHomeworkData()
                } else {
                    mutableLectures.value = lecturesFromMemory
                    mutableLecturesState.value = LecturesState.LOADED_FROM_MEM
                }
            }
        }
    }

    private fun updateHomeworkData() {
        val cookie = getCookieFromMemory()
        val newLectures: ArrayList<DBLecture> = arrayListOf()
        fintechService.getHomeworks(cookie).enqueue(object : Callback<HomeworksContainer> {
            override fun onFailure(call: Call<HomeworksContainer>, t: Throwable) {
                mutableLecturesState.value = LecturesState.NOT_LOADED
            }

            override fun onResponse(call: Call<HomeworksContainer>, response: Response<HomeworksContainer>) {
                if (response.isSuccessful) {
                    val homeworkContainer = response.body()
                    val homeworks: ArrayList<DBHomework> = arrayListOf()
                    for (lecture in homeworkContainer!!.lectures) {
                        newLectures.add(convertLecture(lecture))
                        getHomeworksOfLecture(lecture).forEach { homeworks.add(it) }
                    }
                    newLectures.forEach { lec -> database.getLectureDao().insert(lec) }
                    homeworks.forEach { hw -> database.getHomeworkDao().insert(hw) }
                    mutableLectures.value = newLectures.sortedBy { it.id }
                    mutableLecturesState.value = LecturesState.LOADED_FROM_NETWORK
                }
            }
        })
    }

    private fun getCookieFromMemory(): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(COOKIE_NAME, "")!!
    }

    companion object {
        const val PREFS_NAME = "ru.lab.fintechcourseproject"
        const val COOKIE_NAME = "cookie"
    }

    enum class LecturesState {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}

