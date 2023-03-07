package com.epitech.soraeven.controller

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.profil.ProfilUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSettingsActivity : AppCompatActivity() {
    private lateinit var settingsDisplayName: TextView
    private lateinit var settingsUserName: TextView
    private lateinit var iconImg: ImageView
    private lateinit var displayProfile: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        //profile text views setup
        settingsDisplayName = findViewById(R.id.settings_display_name)
        settingsUserName = findViewById(R.id.settings_user_name)
        iconImg = findViewById(R.id.icon_img)
        displayProfile = findViewById(R.id.displayProfile)

        //profile infos
        RedditClient.getProfile(object : Callback<ProfilUser?> {
            override fun onResponse(call: Call<ProfilUser?>, response: Response<ProfilUser?>) {
                val responseData: ProfilUser? = response.body()
                settingsDisplayName.text = responseData?.name
                if(responseData?.subreddit?.subreddit_type == "user") {
                    val username = StringBuilder()
                    username.append("u/").append(responseData.name)
                    settingsUserName.text = username
                }

                //images
                val imageLoading = ImageLoading()
                imageLoading.simpleImageViewIntegration(this@UserSettingsActivity, responseData?.subreddit?.icon_img, iconImg)
                imageLoading.customViewIntegration(this@UserSettingsActivity, responseData?.subreddit?.banner_img, displayProfile)
            }

            override fun onFailure(call: Call<ProfilUser?>, t: Throwable) {

            }
        })
    }
}