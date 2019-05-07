package ru.lab.fintechcourseproject.appearance.lecturesScreen

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import javax.inject.Inject

class LecturesPresenter @Inject constructor(private val lecturesModel: LecturesModel) :
    MvpBasePresenter<ILecturesView>() {
    init {
        lecturesModel.lecturesState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    LecturesModel.LecturesState.LOADED_FROM_NETWORK -> view.onResult(lecturesModel.lectures.value!!)
                    LecturesModel.LecturesState.LOADED_FROM_MEM -> view.onResult(lecturesModel.lectures.value!!)
                    LecturesModel.LecturesState.IN_PROCESS -> view.onLoadingProgress()
                    LecturesModel.LecturesState.NOT_LOADED -> view.onFailure()
                    else -> TODO()
                }
            }
        }
    }

    fun loadLectures(source: DataSource = DataSource.UNSPECIFIED) = lecturesModel.loadLectures(source)

    enum class DataSource {
        NETWORK, MEMORY, UNSPECIFIED
    }
}
