package com.example.cosmea.ui

import com.example.cosmea.R
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)

        val loginTextView = findViewById<TextView>(R.id.loginTextView)
        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {

        }
    }
}
