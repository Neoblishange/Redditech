package com.epitech.soraeven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    val loginButton = findViewById(R.id.loginButton) as Button
    loginButton.setOnClickListener {
        Toast.makeText(this.@MainActivity, "You clicked me", Toast.LENGTH_SHORT)
    }
}