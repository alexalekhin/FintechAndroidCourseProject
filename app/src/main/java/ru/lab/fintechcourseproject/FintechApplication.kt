package ru.lab.fintechcourseproject

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.lab.fintechcourseproject.appearance.lecturesScreen.LecturesModel
import ru.lab.fintechcourseproject.appearance.loginScreen.LoginModel
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses.CourseModel
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance.PerformanceModel
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating.RatingModel
import ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab.EventsModel
import ru.lab.fintechcourseproject.appearance.mainScreen.profileTab.UserProfileModel
import ru.lab.fintechcourseproject.appearance.performanceScreen.StudentsModel
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.di.ContextModule
import ru.lab.fintechcourseproject.di.DaggerApplicationComponent
import ru.lab.fintechcourseproject.di.DataBaseModule
import ru.lab.fintechcourseproject.di.NetworkModule
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.RetrofitNetworkClient
import javax.inject.Inject

class FintechApplication : Application() {
    //todo: fix DI
    @Inject
    lateinit var fintechService: NetworkService
    @Inject
    lateinit var appDb: AppDb
    @Inject
    lateinit var loginModel: LoginModel
    @Inject
    lateinit var userProfileModel: UserProfileModel
    @Inject
    lateinit var lecturesModel: LecturesModel
    @Inject
    lateinit var studentsModel: StudentsModel
    @Inject
    lateinit var eventsModel: EventsModel
    @Inject
    lateinit var courseModel: CourseModel
    @Inject
    lateinit var performanceModel: PerformanceModel
    @Inject
    lateinit var ratingModel: RatingModel

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule(RetrofitNetworkClient))
            .contextModule(ContextModule(this))
            .dataBaseModule(DataBaseModule())
            .build().inject(this)
        AndroidThreeTen.init(this)
    }
}


enum class ModelType {
    LOGIN_MODEL, USER_MODEL, LECTURES_MODEL, STUDENTS_MODEL, EVENTS_MODEL, COURSES_MODEL, COURSE_PROGRESS_MODEL, RATING_MODEL
}

interface Model


fun getModel(context: Context, modelType: ModelType): Model {
    return when (modelType) {
        ModelType.LOGIN_MODEL -> (context.applicationContext as FintechApplication).loginModel
        ModelType.USER_MODEL -> (context.applicationContext as FintechApplication).userProfileModel
        ModelType.LECTURES_MODEL -> (context.applicationContext as FintechApplication).lecturesModel
        ModelType.STUDENTS_MODEL -> (context.applicationContext as FintechApplication).studentsModel
        ModelType.EVENTS_MODEL -> (context.applicationContext as FintechApplication).eventsModel
        ModelType.COURSES_MODEL -> (context.applicationContext as FintechApplication).courseModel
        ModelType.COURSE_PROGRESS_MODEL -> (context.applicationContext as FintechApplication).performanceModel
        ModelType.RATING_MODEL -> (context.applicationContext as FintechApplication).ratingModel
    }
}
