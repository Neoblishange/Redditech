package com.epitech.soraeven.controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.R
import com.epitech.soraeven.model.AccessToken
import com.epitech.soraeven.model.DataPostResult
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LandingActivity : AppCompatActivity() {
    // Right clientId
    private val clientId = "DmJqy_hwmMfuC0b1YMn43g"
    // Test clientId
    // private val clientId = "NXr5v260lGrah-KD6xWCsw"
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private lateinit var mNavigateHomeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        mNavigateHomeButton = findViewById<Button>(R.id.redirectButtonToHome)
        mNavigateHomeButton.setOnClickListener {
            //val intent = Intent(this@LandingActivity, MainActivity::class.java)
            //startActivity(intent)
            testRequest()
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
                    client.getAccessToken(credentials,
                        "authorization_code",
                        redirectUri.toString(),
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
    fun testRequest() {
        /*val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val interceptorClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()*/
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl("https://oauth.reddit.com")
            /*.client(interceptorClient)*/ //this line added
            .addConverterFactory(GsonConverterFactory.create(/*gson*/))
        val retrofit: Retrofit = builder.build()
        val client = retrofit.create(RedditClient::class.java)
        val preferences = getSharedPreferences("my_app", Context.MODE_PRIVATE)
        val accessToken = preferences.getString("access_token", null)
        client.getFilteredPost("hot", "3", "bearer " + accessToken)
            ?.enqueue(object : Callback<DataPostResult?> {
                override fun onFailure(call: Call<DataPostResult?>, t: Throwable) {
                    Toast.makeText(
                        this@LandingActivity,
                        "No!", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<DataPostResult?>,
                    response: Response<DataPostResult?>
                ) {
                    Toast.makeText(
                        this@LandingActivity,
                        "Yeah!", Toast.LENGTH_SHORT
                    ).show()
                    // We can retrieve the access token by doing response.body()?.getAccessToken()
                    val responseData: DataPostResult? = response.body()
                    println(responseData)
                }
            })
    }
}