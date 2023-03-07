package com.epitech.soraeven.controller

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.substring
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.epitech.soraeven.Footer
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.DataPostResult
import com.epitech.soraeven.model.profil.ProfilUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var mNavigateUserSettingsIcon: ImageView
    private lateinit var allPostsContainer: LinearLayout
    private lateinit var settingsDisplayName: TextView
    private lateinit var settingsUserName: TextView
    private lateinit var userProfileDescriptionTextView: TextView
    private lateinit var iconImg: ImageView
    private lateinit var displayProfile: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mNavigateUserSettingsIcon = findViewById<ImageView>(R.id.settings_icon)
        mNavigateUserSettingsIcon.setOnClickListener {
            val intent = Intent(this@ProfileActivity, UserSettingsActivity::class.java)
            startActivity(intent)
        }

        val footer = Footer()
        footer.setupFooter(this@ProfileActivity)

        allPostsContainer = findViewById(R.id.allPostsLayout)
        displayPost(7, allPostsContainer)

        //profile text views setup
        settingsDisplayName = findViewById(R.id.settings_display_name)
        settingsUserName = findViewById(R.id.settings_user_name)
        userProfileDescriptionTextView = findViewById(R.id.user_profile_description_text_view)
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
                userProfileDescriptionTextView.text = responseData?.subreddit?.public_description

                //images
                val imageLoading = ImageLoading()
                imageLoading.simpleImageViewIntegration(this@ProfileActivity, responseData?.subreddit?.icon_img, iconImg)
                imageLoading.customViewIntegration(this@ProfileActivity, responseData?.subreddit?.banner_img, displayProfile)
            }

            override fun onFailure(call: Call<ProfilUser?>, t: Throwable) {

            }
        })
    }

    private fun displayPost(numberOfViews: Int, container: ViewGroup) {
        for (i in 0 until numberOfViews) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.post, container, false)
            view.tag = "community_icon"
            container.addView(view)
        }
    }
}