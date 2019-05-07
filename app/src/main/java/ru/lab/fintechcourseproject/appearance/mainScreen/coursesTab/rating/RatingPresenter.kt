package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

class RatingPresenter(private val model: RatingModel): MvpBasePresenter<IRatingView>() {


    fun loadRating(source: DataSource = DataSource.UNSPECIFIED) {
        model.loadRating(source)
        model.ratingState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    RatingModel.State.LOADED_FROM_NETWORK -> view.onResult(model.rating.value!!)
                    RatingModel.State.LOADED_FROM_MEM -> view.onResult(model.rating.value!!)
                    RatingModel.State.IN_PROCESS -> view.onLoadingProgress()
                    RatingModel.State.NOT_LOADED -> view.onFailure()
                    else -> TODO()
                }
            }
        }
    }

    enum class DataSource {
        UPDATE, RELOAD, UNSPECIFIED
    }
}
