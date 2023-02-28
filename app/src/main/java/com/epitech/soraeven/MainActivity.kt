package com.epitech.soraeven

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.api.model.PostSearch
import com.epitech.soraeven.api.service.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginButton: Button
    private lateinit var mRedirectButton: Button
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    @Suppress("SpellCheckingInspection")
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private val scopes = "account modconfig read save mysubreddits subscribe vote"
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

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(authToken = String()))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val PostReasearch = retrofit.create(PostSearch::class.java)




}
