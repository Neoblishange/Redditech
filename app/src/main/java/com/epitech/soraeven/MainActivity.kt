package com.epitech.soraeven

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.api.model.AccessToken
import com.epitech.soraeven.api.service.RedditClient
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginButton: Button
    private lateinit var mRedirectButton: Button
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private val scopes = Uri.parse("read save")
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

        // val loginButton = findViewById(R.id.loginButton) as Button
        mLoginButton = findViewById<Button>(R.id.loginButton)
        mLoginButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, authUrl)
            startActivity(intent)
        }

        // Navigate button
        mRedirectButton = findViewById(R.id.redirectButton)
        mRedirectButton.setOnClickListener {
            val i = Intent(this@MainActivity, LandingActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()

        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(redirectUri.toString())) {
            val code = uri.getQueryParameter("code")

            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://www.reddit.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit: Retrofit = builder.build()

            val client = retrofit.create(RedditClient::class.java)
            val credentials: String = Credentials.basic(clientId, "")
            val accessTokenCall: Call<AccessToken?>? =
                code?.let {
                    client.getAccessToken(credentials, "authorization_code", redirectUri.toString(),
                        it
                    )
                }
            if (accessTokenCall != null) {
                accessTokenCall.enqueue(object: Callback<AccessToken?> {
                    override fun onFailure(call: Call<AccessToken?>, t: Throwable) {
                        Toast.makeText(this@MainActivity,
                            "No!", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<AccessToken?>,
                        response: Response<AccessToken?>
                    ) {
                        Toast.makeText(this@MainActivity,
                            "Yeah!", Toast.LENGTH_SHORT).show()
                        println(code)
                    }
                })
            }


        }
    }
}
