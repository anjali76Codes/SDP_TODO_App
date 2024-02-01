package com.example.loginpage

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

object Utility {

    @JvmStatic
    fun getCollectionReferenceForNotes(): CollectionReference? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return FirebaseFirestore.getInstance().collection("notes")
            .document(currentUser!!.uid).collection("my_notes")
    }

    @JvmStatic
    fun timestampToString(timestamp: Timestamp): String {
        val format = SimpleDateFormat("MM/dd/yyyy")
        return format.format(timestamp.toDate())
    }
}
