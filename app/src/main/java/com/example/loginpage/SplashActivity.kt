package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()

        // Delay for the splash screen
        Handler().postDelayed({
            // Check if the user is already logged in
            if (firebaseAuth.currentUser != null) {
                // User is logged in
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // User is not logged in
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }


}
