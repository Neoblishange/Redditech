package com.epitech.soraeven.controller

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.Footer
import com.epitech.soraeven.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var mNavigateUserSettingsIcon: ImageView
    private lateinit var allPostsContainer: LinearLayout
    private lateinit var profileButton: Button
    private lateinit var homeButton: Button

    private lateinit var footerView: View

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