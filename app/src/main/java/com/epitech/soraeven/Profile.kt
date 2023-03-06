package com.epitech.soraeven

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {
    private lateinit var mUserSettingsImage: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //mUserSettingsImage = findViewById(settings_icon)
    }
}