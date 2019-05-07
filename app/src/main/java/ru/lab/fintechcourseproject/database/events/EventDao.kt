package ru.lab.fintechcourseproject.database.homeworks

import androidx.room.*
import ru.lab.fintechcourseproject.database.events.DBEvent


@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hw: DBEvent)

    @Update
    fun update(hw: DBEvent)

    @Query("SELECT * FROM events WHERE status = \"active\"")
    fun getActiveEvents(): List<DBEvent>

    @Query("SELECT * FROM events WHERE status = \"archived\"")
    fun getArchivedEvents(): List<DBEvent>
}
