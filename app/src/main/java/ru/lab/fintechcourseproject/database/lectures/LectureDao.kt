package ru.lab.fintechcourseproject.database.lectures

import androidx.room.*

@Dao
interface LectureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lec: DBLecture)

    @Update
    fun update(lec: DBLecture)

    @Query("SELECT * FROM lectures")
    fun getLectures(): List<DBLecture>
}
