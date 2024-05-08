package com.example.cosmea.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.cosmea.R

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        var showPasswordButton = findViewById<ImageButton>(R.id.showPasswordButton)

        showPasswordButton.setOnClickListener {
            if (editTextPassword.transformationMethod == null) {
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                showPasswordButton.setImageResource(R.drawable.ic_eye)
            } else {
                editTextPassword.transformationMethod = null
                showPasswordButton.setImageResource(R.drawable.ic_eye)
            }
            editTextPassword.setSelection(editTextPassword.text.length)
        }

        val registerTextView = findViewById<TextView>(R.id.registerTextView)
        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

        }

    }
}
