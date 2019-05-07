package ru.lab.fintechcourseproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lab.fintechcourseproject.database.courses.CoursesDao
import ru.lab.fintechcourseproject.database.courses.DBCourse
import ru.lab.fintechcourseproject.database.events.DBEvent
import ru.lab.fintechcourseproject.database.homeworks.DBHomework
import ru.lab.fintechcourseproject.database.homeworks.EventDao
import ru.lab.fintechcourseproject.database.homeworks.HomeworkDao
import ru.lab.fintechcourseproject.database.lectures.DBLecture
import ru.lab.fintechcourseproject.database.lectures.LectureDao
import ru.lab.fintechcourseproject.database.students.DBStudent
import ru.lab.fintechcourseproject.database.students.StudentsDao


@Database(entities = [DBLecture::class, DBHomework::class, DBStudent::class, DBEvent::class, DBCourse::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun getLectureDao(): LectureDao
    abstract fun getHomeworkDao(): HomeworkDao
    abstract fun getStudentDao(): StudentsDao
    abstract fun getEventDao(): EventDao
    abstract fun getCoursesDao(): CoursesDao
}
