package br.com.ivan.simplenote.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.ivan.simplenote.database.entity.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

}