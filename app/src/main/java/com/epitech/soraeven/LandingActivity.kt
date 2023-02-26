package com.epitech.soraeven

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.api.model.AccessToken
import com.epitech.soraeven.api.service.RedditClient
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import retrofit2.Callback
import retrofit2.Response

class LandingActivity : AppCompatActivity() {
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private lateinit var mNavigateHomeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        mNavigateHomeButton = findViewById<Button>(R.id.redirectButtonToHome)
        mNavigateHomeButton.setOnClickListener {
            val intent = Intent(this@LandingActivity, MainActivity::class.java)
            startActivity(intent)
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
                        Toast.makeText(this@LandingActivity,
                            "No!", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<AccessToken?>,
                        response: Response<AccessToken?>
                    ) {
                        Toast.makeText(this@LandingActivity,
                            "Yeah!", Toast.LENGTH_SHORT).show()
                        // We can retrieve the access token by doing response.body()?.getAccessToken()
                        println(response.body()?.getAccessToken())
                        // Store the access key in the shared preferences
                        val preferences = getSharedPreferences("my_app", Context.MODE_PRIVATE)
                        val editor = preferences.edit()
                        editor.putString("access_token", response.body()?.getAccessToken())
                        editor.apply()
                        /* To retrieve the access key :
                        * val preferences = getSharedPreferences("my_app", Context.MODE_PRIVATE)
                        * val accessToken = preferences.getString("access_token", null)
                        * */

                    }
                })
            }
        }
    }
}