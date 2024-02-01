package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginpage.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.bottom.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {

            var Email = binding.email.text.toString()
            var Password = binding.password.text.toString()
            var Repassword = binding.repassword.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty() && Repassword.isNotEmpty()) {
                if (Password == Repassword) {
                    firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password Not Matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Input Fields Cannot be Empty", Toast.LENGTH_SHORT).show()

            }
        }
    }
}