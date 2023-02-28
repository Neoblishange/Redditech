package com.epitech.soraeven

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginButton: Button
    private lateinit var mRedirectButton: Button
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
            val intent = Intent(Intent.ACTION_VIEW, authUrl)
            startActivity(intent)
        }

        mRedirectButton = findViewById(R.id.redirectButton)
        mRedirectButton.setOnClickListener {
            val i = Intent(this@MainActivity, LandingActivity::class.java)
            startActivity(i)
        }
    }
}
