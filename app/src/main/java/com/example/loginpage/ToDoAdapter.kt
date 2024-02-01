package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat

class ToDoAdapter(options: FirestoreRecyclerOptions<ToDo>, private val context: Context) :
    FirestoreRecyclerAdapter<ToDo, ToDoAdapter.NoteViewHolder>(options) {

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: ToDo) {
        holder.titleTextView.text = model.title
        holder.contentTextView.text = model.content

        val timestampFormat = SimpleDateFormat("MM/dd/yyyy")
        val formattedTimestamp = model.timestamp?.toDate()?.let { timestampFormat.format(it) } ?: ""
        holder.timestampTextView.text = formattedTimestamp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ToDoActivity::class.java)
            intent.putExtra("title", model.title)
            intent.putExtra("content", model.content)
            val docId = snapshots.getSnapshot(position).id
            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_task_item, parent, false)
        return NoteViewHolder(view)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var contentTextView: TextView
        var timestampTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.note_title_text_view)
            contentTextView = itemView.findViewById(R.id.note_content_text_view)
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view)
        }
    }
}
