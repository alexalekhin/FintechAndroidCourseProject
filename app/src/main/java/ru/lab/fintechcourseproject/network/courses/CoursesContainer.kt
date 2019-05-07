package ru.lab.fintechcourseproject.network.courses

import com.google.gson.annotations.SerializedName
import ru.lab.fintechcourseproject.database.courses.DBCourse

fun convertToDatabaseCourse(course: Course): DBCourse {
    return DBCourse(
        course.title,
        course.status,
        course.date,
        course.url
    )
}

class CoursesContainer(
    @SerializedName("courses")
    val courses: ArrayList<Course>
)
