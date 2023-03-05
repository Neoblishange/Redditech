package com.epitech.soraeven.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var mNavigateUserSettingsIcon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mNavigateUserSettingsIcon = findViewById<ImageView>(R.id.settings_icon)
        mNavigateUserSettingsIcon.setOnClickListener {
            val intent = Intent(this@ProfileActivity, UserSettingsActivity::class.java)
            startActivity(intent)
        }

    }
}