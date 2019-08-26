package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

class PerformancePresenter(private val model: PerformanceModel) : MvpBasePresenter<IPerformanceView>() {

    fun loadStudents(source: DataSource = DataSource.UNSPECIFIED) {
        model.loadStudents(source)
        model.state.observeForever {
            ifViewAttached { view ->
                when (it) {
                    PerformanceModel.State.LOADED_FROM_NETWORK -> view.onResult(
                        //TODO: remove hot fix
                        emptyList()

//                        model.students.value!!.subList(
//                            LIST_BEGINNING,
//                            LIST_ENDING
//                        )
                    )
                    PerformanceModel.State.LOADED_FROM_MEM -> view.onResult(
                        emptyList()
//                        model.students.value!!.subList(
//                            LIST_BEGINNING,
//                            LIST_ENDING
//                        )
                    )
                    PerformanceModel.State.IN_PROCESS -> view.onLoadingProgress()
                    PerformanceModel.State.NOT_LOADED -> view.onFailure()
                    else -> view.onResult(emptyList())
                }
            }
        }
    }

    companion object {
        const val LIST_BEGINNING = 0
        const val LIST_ENDING = 10
    }

    enum class DataSource {
        UPDATE, RELOAD, UNSPECIFIED
    }
}
