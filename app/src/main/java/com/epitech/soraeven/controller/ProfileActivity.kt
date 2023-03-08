package com.epitech.soraeven.controller

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.Footer
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.PostList
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
    private lateinit var imagesProfile: ConstraintLayout
    private lateinit var usernamesProfile: ConstraintLayout

    private lateinit var postReddit : Array<PostList.DataPostList.ChildrenPost>

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
        displayPost(7, allPostsContainer, "new")

        //profile text views setup
        settingsDisplayName = findViewById(R.id.settings_display_name)
        settingsUserName = findViewById(R.id.settings_user_name)
        userProfileDescriptionTextView = findViewById(R.id.user_profile_description_text_view)
        iconImg = findViewById(R.id.icon_img)
        imagesProfile = findViewById(R.id.imagesProfile)
        usernamesProfile = findViewById(R.id.usernamesProfile)

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
                imageLoading.customViewIntegration(this@ProfileActivity, responseData?.subreddit?.banner_img, imagesProfile)
            }

            override fun onFailure(call: Call<ProfilUser?>, t: Throwable) {

            }
        })
    }

    private fun displayPost(numberOfViews: Int, container: ViewGroup , filter: String) {

        RedditClient.getFilteredPost(filter, 3, object : Callback<PostList?> {
            override fun onFailure(call: Call<PostList?>, t: Throwable) {
                // Handle the failure case
            }

            override fun onResponse(call: Call<PostList?>, response: Response<PostList?>) {
                // Handle the success case
                val responseData: PostList? = response.body()
                postReddit = responseData?.data?.children!!
                for (i in 0 until postReddit?.size as Int) {
                    val view = LayoutInflater.from(container.context)
                        .inflate(R.layout.post, container, false)
                    view.tag = "community_icon"
                    PostDataFilling.fillPost(view, postReddit[i].data)
                    println("yoooo "+ i +" = "+ postReddit[i].data.title )
                    container.addView(view)

                }
            }
        })
    }
}