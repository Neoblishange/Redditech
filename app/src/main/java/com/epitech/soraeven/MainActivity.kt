package com.epitech.soraeven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val loginButton = findViewById(R.id.loginButton) as Button
        mLoginButton = findViewById(R.id.loginButton) as Button
        mLoginButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me", Toast.LENGTH_SHORT).show()
        }
    }
}