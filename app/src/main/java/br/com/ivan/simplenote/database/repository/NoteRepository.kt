package br.com.ivan.simplenote.database.repository

import androidx.lifecycle.LiveData
import br.com.ivan.simplenote.database.dao.NotesDao
import br.com.ivan.simplenote.database.entity.Note

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }

}