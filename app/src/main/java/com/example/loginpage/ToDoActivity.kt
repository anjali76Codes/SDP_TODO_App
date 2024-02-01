package com.example.loginpage

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.Task
import android.view.View
import android.widget.Toast

class ToDoActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveNoteBtn: ImageButton
    private lateinit var pageTitleTextView: TextView
    private lateinit var deleteNoteTextViewBtn: TextView

    private var noteTitle: String? = null
    private var noteContent: String? = null
    private var noteDocId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        titleEditText = findViewById(R.id.notes_title_text)
        contentEditText = findViewById(R.id.notes_content_text)
        saveNoteBtn = findViewById(R.id.save_note_btn)
        pageTitleTextView = findViewById(R.id.page_title)
        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn)

        // receive data
        noteTitle = intent.getStringExtra("title")
        noteContent = intent.getStringExtra("content")
        noteDocId = intent.getStringExtra("docId")

        if (noteDocId != null && noteDocId!!.isNotEmpty()) {
            isEditMode = true
        }

        titleEditText.setText(noteTitle)
        contentEditText.setText(noteContent)
        if (isEditMode) {
            pageTitleTextView.text = "Edit your task"
            deleteNoteTextViewBtn.visibility = View.VISIBLE
        }

        saveNoteBtn.setOnClickListener { saveNote() }
        deleteNoteTextViewBtn.setOnClickListener { deleteNoteFromFirebase() }
    }

    private fun saveNote() {
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()
        if (title.isNullOrBlank()) {
            titleEditText.error = "Title is required"
            return
        }

        val toDo = ToDo()
        toDo.title = title
        toDo.content = content
        toDo.timestamp = Timestamp.now()

        saveNoteToFirebase(toDo)
    }

    private fun saveNoteToFirebase(toDo: ToDo) {
        val documentReference: DocumentReference? = if (isEditMode) {
            Utility.getCollectionReferenceForNotes()?.document(noteDocId!!)
        } else {
            Utility.getCollectionReferenceForNotes()?.document()
        }

        documentReference?.let { docRef ->
            docRef.set(toDo).addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    // note is added
                    Toast.makeText(this@ToDoActivity, "Note added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@ToDoActivity, "Failed to add Notes", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun deleteNoteFromFirebase() {
        val documentReference: DocumentReference? = Utility.getCollectionReferenceForNotes()?.document(noteDocId!!)

        documentReference?.delete()?.addOnCompleteListener { task: Task<Void?> ->
            if (task.isSuccessful) {
                // note is deleted
                Toast.makeText(this@ToDoActivity, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@ToDoActivity, "Failed to delete note", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
