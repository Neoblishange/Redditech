package com.epitech.soraeven.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.R

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginButton: ImageButton
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    @Suppress("SpellCheckingInspection")
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private val scopes = "account identity modconfig read save mysubreddits subscribe vote"
    private val authUrl = Uri.parse("https://www.reddit.com/api/v1/authorize.compact" +
            "?client_id=$clientId" +
            "&response_type=code" +
            "&state=uniqueString2" +
            "&redirect_uri=$redirectUri" +
            "&duration=permanent" +
            "&scope=$scopes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLoginButton = findViewById(R.id.loginButton)
        mLoginButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("fromActivity", "MainActivity")
            editor.apply()
            val intent = Intent(Intent.ACTION_VIEW, authUrl)
            startActivity(intent)
        }
    }
}
