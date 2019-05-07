package ru.lab.fintechcourseproject.database.courses

import androidx.room.*

@Dao
interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hw: DBCourse)

    @Update
    fun update(hw: DBCourse)

    @Query("SELECT * FROM courses")
    fun getCourses(): List<DBCourse>
}
