package br.com.ivan.simplenote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.ivan.simplenote.database.NoteDatabase
import br.com.ivan.simplenote.database.entity.Note
import br.com.ivan.simplenote.database.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

   val allNotes : LiveData<List<Note>>
   val repository: NoteRepository

   init {
       val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
       allNotes = repository.allNotes
   }

   fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
       repository.delete(note)
   }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

}