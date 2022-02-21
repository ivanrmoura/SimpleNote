package br.com.ivan.simplenote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.ivan.simplenote.database.entity.Note
import br.com.ivan.simplenote.databinding.ActivityMainBinding
import br.com.ivan.simplenote.view.AddEditNoteActivity
import br.com.ivan.simplenote.view.NoteClickDeleteInterface
import br.com.ivan.simplenote.view.NoteClickInterface
import br.com.ivan.simplenote.view.NoteRVAdapter
import br.com.ivan.simplenote.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModal: NoteViewModel
    //lateinit var notesRV: RecyclerView
    //lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        //notesRV = findViewById(R.id.notesRV)
        //addFAB = findViewById(R.id.idFAB)

        binding.notesRV.layoutManager = LinearLayoutManager(this)
        val noteRVAdapter = NoteRVAdapter(this, this, this)
        binding.notesRV.adapter = noteRVAdapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)


        viewModal.allNotes.observe(this,
            Observer { list ->
                list.let {
                    noteRVAdapter.updateList(it)
                }
            })

        binding.idFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }


    }
    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        // displaying a toast message
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        // opening a new intent and passing a data to it.
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }
}