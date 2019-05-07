package ru.lab.fintechcourseproject.database.students

import androidx.room.*

@Dao
interface StudentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: DBStudent)
    @Update
    fun update(student: DBStudent)
    @Query("SELECT * FROM students")
    fun getAllStudents(): List<DBStudent>
}
