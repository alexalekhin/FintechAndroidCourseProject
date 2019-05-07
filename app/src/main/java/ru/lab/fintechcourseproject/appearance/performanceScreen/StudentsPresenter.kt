package ru.lab.fintechcourseproject.appearance.performanceScreen

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import javax.inject.Inject

class StudentsPresenter @Inject constructor(private val studentsModel: StudentsModel) :
    MvpBasePresenter<IStudentsView>() {

    fun getStudents(source: DataSource = DataSource.UNSPECIFIED) {
        studentsModel.loadStudents(source)
        studentsModel.studentsState.observeForever {
            when (it) {
                StudentsModel.StudentsState.LOADED_FROM_NETWORK -> ifViewAttached { view ->
                    view.onResult(studentsModel.students.value!!)
                }
                StudentsModel.StudentsState.LOADED_FROM_MEM -> ifViewAttached { view ->
                    view.onResult(studentsModel.students.value!!)
                }
                StudentsModel.StudentsState.IN_PROCESS -> ifViewAttached { view ->
                    view.onLoadingProgress()
                }
                StudentsModel.StudentsState.NOT_LOADED -> ifViewAttached { view ->
                    view.onFailure()
                }
                else -> TODO()
            }
        }
    }

    enum class DataSource {
        UPDATE, RELOAD, UNSPECIFIED
    }
}
