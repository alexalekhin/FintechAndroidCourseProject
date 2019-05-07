package ru.lab.fintechcourseproject.network

import retrofit2.Call
import retrofit2.http.*
import ru.lab.fintechcourseproject.network.courses.CourseAbout
import ru.lab.fintechcourseproject.network.courses.CoursesContainer
import ru.lab.fintechcourseproject.network.events.EventsContainer
import ru.lab.fintechcourseproject.network.lectures.HomeworksContainer
import ru.lab.fintechcourseproject.network.students.StudentsContainer
import ru.lab.fintechcourseproject.network.users.Profile

interface NetworkService {
    @Headers(
        "Content-Type: application/json"
    )
    @POST("api/login")
    fun authorize(@Body signingInfo: LoginInfo): Call<Any>

    @GET("api/user")
    fun getProfile(@Header("Cookie") cookie: String): Call<Profile>

    @GET("api/course/android_spring_2019/grades")
    fun getStudents(@Header("Cookie") cookie: String): Call<ArrayList<StudentsContainer>>

    @GET("api/course/android_spring_2019/homeworks")
    fun getHomeworks(@Header("Cookie") cookie: String): Call<HomeworksContainer>

    @GET("api/connections")
    fun getCourses(@Header("Cookie") cookie: String): Call<CoursesContainer>

    @GET("api/calendar/list/event")
    fun getEvents(): Call<EventsContainer>

    @GET("api/course/android_spring_2019/about")
    fun getCourseAbout(@Header("Cookie") cookie: String/*, @Path("course_id") course_id: String*/ ): Call<CourseAbout>

}
