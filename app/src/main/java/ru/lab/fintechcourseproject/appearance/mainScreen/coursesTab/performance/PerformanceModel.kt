package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

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

class PerformanceModel(
    private val fintechService: NetworkService,
    private val database: AppDb,
    context: Context
) : Model {
    private var prefs: SharedPreferences = context.getSharedPreferences(LoginModel.PREFS_NAME, Context.MODE_PRIVATE)
    private var mutableStudents: MutableLiveData<List<DBStudent>> = MutableLiveData()
    private var mutableState: MutableLiveData<State> = MutableLiveData()
    val students: LiveData<List<DBStudent>> = mutableStudents
    val state: LiveData<State> = mutableState

    fun loadStudents(source: PerformancePresenter.DataSource = PerformancePresenter.DataSource.UNSPECIFIED) {
        mutableState.value = State.IN_PROCESS
        when (source) {
            PerformancePresenter.DataSource.UNSPECIFIED -> {
                mutableStudents.value = database.getStudentDao().getAllStudents().sortedByDescending { it.points }
                if ((mutableStudents.value)!!.isEmpty()) updateStudentsData()
                mutableState.value = State.LOADED_FROM_MEM
            }
            PerformancePresenter.DataSource.RELOAD -> {
                mutableStudents.value = database.getStudentDao().getAllStudents().sortedByDescending { it.points }
                mutableState.value = State.LOADED_FROM_MEM

            }
            PerformancePresenter.DataSource.UPDATE -> updateStudentsData()
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
                    mutableStudents.value = newStudents.sortedByDescending { it.points }
                    newStudents.forEach { database.getStudentDao().insert(it) }//todo: move to backing thread
                    mutableState.value = State.LOADED_FROM_NETWORK
                }

                override fun onFailure(call: Call<ArrayList<StudentsContainer>>, t: Throwable) {
                    mutableState.value = State.NOT_LOADED
                }
            })
    }

    enum class State {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}
