package ru.lab.fintechcourseproject.database.homeworks

import androidx.room.*


@Dao
interface HomeworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hw: DBHomework)

    @Update
    fun update(hw: DBHomework)

    @Query("SELECT * FROM homeworks WHERE lecId = :lecId")
    fun getHomeWorksByLectureId(lecId: Long): List<DBHomework>
}
