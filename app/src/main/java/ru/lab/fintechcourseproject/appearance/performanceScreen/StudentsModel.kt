package ru.lab.fintechcourseproject.appearance.performanceScreen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.appearance.loginScreen.LoginModel
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.database.students.DBStudent
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.students.StudentsContainer
import ru.lab.fintechcourseproject.network.students.convertToDatabaseStudent
import java.util.*
import javax.inject.Inject

class StudentsModel @Inject constructor(
    private val fintechService: NetworkService,
    private val database: AppDb,
    context: Context
) : Model {
    private var prefs: SharedPreferences = context.getSharedPreferences(LoginModel.PREFS_NAME, Context.MODE_PRIVATE)
    private var mutableStudents: MutableLiveData<List<DBStudent>> = MutableLiveData<List<DBStudent>>().apply {
        value = database.getStudentDao().getAllStudents()
    }
    private var mutableStudentsState: MutableLiveData<StudentsState> = MutableLiveData<StudentsState>().apply {
        value = StudentsState.LOADED_FROM_MEM
    }
    private var lastUpdateTime = prefs.getLong(LAST_STUDENTS_UPDATE_TIME_IN_MILLIS, 0)
    val students: LiveData<List<DBStudent>> = mutableStudents
    val studentsState: LiveData<StudentsState> = mutableStudentsState

    fun loadStudents(source: StudentsPresenter.DataSource = StudentsPresenter.DataSource.UNSPECIFIED) {
        when (source) {
            StudentsPresenter.DataSource.UNSPECIFIED -> if ((mutableStudents.value)!!.isEmpty()) updateStudentsData()
            StudentsPresenter.DataSource.RELOAD -> mutableStudents.value = database.getStudentDao().getAllStudents()
            StudentsPresenter.DataSource.UPDATE -> {
                lastUpdateTime = prefs.getLong(LAST_STUDENTS_UPDATE_TIME_IN_MILLIS, 0)
                val currentTime = Calendar.getInstance().timeInMillis
                if (currentTime - lastUpdateTime >= STD_CACHE_TIMEOUT) {
                    updateStudentsData()
                    prefs.edit().putLong(LAST_STUDENTS_UPDATE_TIME_IN_MILLIS, currentTime).apply()
                } else {
                    mutableStudents.value = database.getStudentDao().getAllStudents()
                }
            }
        }
    }

    private fun updateStudentsData() {
        val newStudents: ArrayList<DBStudent> = arrayListOf()

        fintechService.getStudents(prefs.getString("cookie", "")!!).enqueue(
            object : Callback<ArrayList<StudentsContainer>> {
                override fun onResponse(
                    call: Call<ArrayList<StudentsContainer>>,
                    response: Response<ArrayList<StudentsContainer>>
                ) {
                    val studentsContainer: StudentsContainer = response.body()!![1]
                    for (student in studentsContainer.students) {
                        newStudents.add(convertToDatabaseStudent(student))
                    }
                    mutableStudents.value = newStudents
                    newStudents.forEach { database.getStudentDao().insert(it) }//todo: move to backing thread
                    mutableStudentsState.value = StudentsState.LOADED_FROM_NETWORK
                }

                override fun onFailure(call: Call<ArrayList<StudentsContainer>>, t: Throwable) {
                    mutableStudentsState.value = StudentsState.NOT_LOADED
                }
            })

    }

    companion object {
        const val LAST_STUDENTS_UPDATE_TIME_IN_MILLIS = "last_students_update_time"
        const val STD_CACHE_TIMEOUT: Long = 10_000L

        @Inject
        fun newInstance(fs: NetworkService, db: AppDb, context: Context) = StudentsModel(fs, db, context)
    }

    enum class StudentsState {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}
