package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginpage.Utility.getCollectionReferenceForNotes
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity() {
    private var addNoteBtn: FloatingActionButton? = null
    private var menuBtn: ImageButton? = null
    var recyclerView: RecyclerView? = null
    var toDoAdapter: ToDoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNoteBtn = findViewById(R.id.add_note_btn)
        menuBtn = findViewById(R.id.menu_btn)
        recyclerView = findViewById(R.id.recyler_view);

        addNoteBtn?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ToDoActivity::class.java))
        }

        menuBtn?.setOnClickListener {
            logout()
        }
        setupRecyclerView();

    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    fun setupRecyclerView() {
        val query: Query = getCollectionReferenceForNotes()!!
            .orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<ToDo>()
            .setQuery(query, ToDo::class.java).build()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        toDoAdapter = ToDoAdapter(options, this)
        recyclerView!!.adapter = toDoAdapter
    }

    override fun onStart() {
        super.onStart()
        toDoAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        toDoAdapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        toDoAdapter!!.notifyDataSetChanged()
    }
}
