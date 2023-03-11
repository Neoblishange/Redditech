package com.epitech.soraeven.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.epitech.soraeven.model.AccessToken
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditAuthenticator(private val context: Context) {
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    private val redirectUri = "soraeven://oauth2redirect"
    private val clientSecret = ""

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit: Retrofit = builder.build()
    private val client = retrofit.create(RedditInterface::class.java)

    fun authenticate(code: String, callback: (Boolean) -> Unit) {
        val credentials: String = Credentials.basic(clientId, clientSecret)
        val accessTokenCall: Call<AccessToken?>? =
            client.getAccessToken(credentials,
                "authorization_code",
                redirectUri,
                code
            )
        accessTokenCall?.enqueue(object: Callback<AccessToken?> {
            override fun onFailure(call: Call<AccessToken?>, t: Throwable) {
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                callback(false)
            }
            override fun onResponse(
                call: Call<AccessToken?>,
                response: Response<AccessToken?>
            ) {
                val accessToken = response.body()?.getAccessToken()
                if (accessToken != null) {
                    val preferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("access_token", accessToken)
                    editor.apply()
                    Log.d("AUTHENTICATION", "Access_token: $accessToken")
                    callback(true)
                }
            }
        })
    }
}