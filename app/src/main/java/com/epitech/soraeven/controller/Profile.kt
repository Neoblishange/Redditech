package com.epitech.soraeven.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.epitech.soraeven.R

class Profile : AppCompatActivity() {
    private lateinit var mUserSettingsImage: AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mUserSettingsImage = findViewById(R.id.settings_icon)
        mUserSettingsImage.setOnClickListener{
            val intent = Intent(this@Profile, UserSettingsActivity::class.java)
            startActivity(intent)
        }
    }
}