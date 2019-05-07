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
    lateinit var loginModel: LoginModel
    @Inject
    lateinit var userProfileModel: UserProfileModel
    @Inject
    lateinit var lecturesModel: LecturesModel
    @Inject
    lateinit var studentsModel: StudentsModel
    @Inject
    lateinit var eventsModel: EventsModel
    lateinit var courseModel: CourseModel
    lateinit var performanceModel: PerformanceModel
    lateinit var ratingModel: RatingModel

    override fun onCreate() {
        super.onCreate()
        fintechService = RetrofitNetworkClient.newNetworkService()
        appDb = DaggerApplicationComponent.builder().contextModule(ContextModule(this)).networkModule(NetworkModule(RetrofitNetworkClient)).build().getDatabase()
        loginModel = LoginModel(fintechService)
        userProfileModel = UserProfileModel(RetrofitNetworkClient.newNetworkService(), this)
        lecturesModel = LecturesModel(RetrofitNetworkClient.newNetworkService(), appDb, this)
        studentsModel = StudentsModel(RetrofitNetworkClient.newNetworkService(), appDb, this)
        eventsModel = EventsModel(fintechService, appDb)
        courseModel = CourseModel(fintechService, appDb, this)
        performanceModel = PerformanceModel(fintechService, appDb, this)
        ratingModel = RatingModel(fintechService, appDb, this)
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
