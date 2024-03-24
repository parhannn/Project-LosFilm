package com.example.pamuts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pamuts.databinding.RegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(){
    private lateinit var binding: RegisterScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passET.text.toString()
            val confirmPassword = binding.retypePassET.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Please Insert All Fields!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.navToLogin.setOnClickListener {
            finish()
        }
    }

}