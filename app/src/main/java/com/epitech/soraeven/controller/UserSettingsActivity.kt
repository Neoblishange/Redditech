package com.epitech.soraeven.controller

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.epitech.soraeven.R
import com.epitech.soraeven.model.profil.UserSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSettingsActivity : AppCompatActivity(), View.OnClickListener {
    private val USER_PREFERENCES = "USER_PREFERENCES"
    private val USER_PREFERENCES_ARE_SAVED = "USER_PREFERENCES_ARE_SAVED"
    private val USER_PREFERENCES_LANG = "USER_PREFERENCES_LANG"
    private val USER_PREFERENCES_ENABLE_FOLLOWERS = "USER_PREFERENCES_ENABLE_FOLLOWERS"
    private val USER_PREFERENCES_NO_PROFANITY = "USER_PREFERENCES_NO_PROFANITY"
    private val USER_PREFERENCES_HIDE_ADS = "USER_PREFERENCES_HIDE_ADS"
    private val USER_PREFERENCES_TOP_KARMA = "USER_PREFERENCES_TOP_KARMA"
    private val USER_PREFERENCES_VIDEO_AUTOPLAY = "USER_PREFERENCES_VIDEO_AUTOPLAY"

    private var mUserSettings: UserSettings? = null
    private var mUserSettingsAreFetched = false

    private lateinit var mDisplayLanguage: TextView
    private lateinit var mEnableFollowers: SwitchCompat
    private lateinit var mNoProfanity: SwitchCompat
    private lateinit var mHideAds: SwitchCompat
    private lateinit var mActiveInCommunities: SwitchCompat
    private lateinit var mAutoplayVideos: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        mDisplayLanguage = findViewById(R.id.selected_language_text_view)
        mEnableFollowers = findViewById(R.id.allow_follower_switch)
        mNoProfanity = findViewById(R.id.nsfw_switch)
        mHideAds = findViewById(R.id.hide_ads_switch)
        mActiveInCommunities = findViewById(R.id.active_in_communities_switch)
        mAutoplayVideos = findViewById(R.id.autoplay_videos_switch)

        mDisplayLanguage.setOnClickListener(this)
        mEnableFollowers.setOnClickListener(this)
        mNoProfanity.setOnClickListener(this)
        mHideAds.setOnClickListener(this)
        mActiveInCommunities.setOnClickListener(this)
        mAutoplayVideos.setOnClickListener(this)

        getUserPreferences()
        if(!mUserSettingsAreFetched) {
            fetchAndSetUserSettings()
        } else mUserSettings?.let { setUserSettings(it) }
    }

    private fun fetchAndSetUserSettings() {
        RedditClient.getUserSettings(object : Callback<UserSettings?> {
            override fun onFailure(call: Call<UserSettings?>, t: Throwable) {
                Toast.makeText(this@UserSettingsActivity, "Problem in fetch!", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<UserSettings?>, response: Response<UserSettings?>) {
                val responseUserSettings = response.body()
                responseUserSettings?.let { saveUserPreferences(it) }
                if (responseUserSettings != null) {
                    setUserSettings(responseUserSettings)
                }
            }
        })
    }

    private fun setUserSettings(userSettings: UserSettings) {
        mDisplayLanguage.text = userSettings.lang
        mEnableFollowers.isChecked = userSettings.enableFollowers
        mNoProfanity.isChecked = userSettings.noProfanity
        mHideAds.isChecked = userSettings.hideAds
        mActiveInCommunities.isChecked = userSettings.activeInCommunities
        mAutoplayVideos.isChecked = userSettings.videoAutoplay
    }

    private fun getUserPreferences() {
        val  userSettingsAreSaved = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
            .getBoolean(USER_PREFERENCES_ARE_SAVED, false)
        if (userSettingsAreSaved) {
            mUserSettings = UserSettings(
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_LANG, null).toString(),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_ENABLE_FOLLOWERS, null).toBoolean(),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_NO_PROFANITY, null).toBoolean(),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_HIDE_ADS, null).toBoolean(),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_TOP_KARMA, null).toBoolean(),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getString(USER_PREFERENCES_VIDEO_AUTOPLAY, null).toBoolean()
            )/*
            mUserSettings?.lang = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_LANG, null).toString()
            mUserSettings.enableFollowers = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_ENABLE_FOLLOWERS, null).toBoolean()
            mUserSettings.noProfanity = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_NO_PROFANITY, null).toBoolean()
            mUserSettings.hideAds = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_HIDE_ADS, null).toBoolean()
            mUserSettings.activeInCommunities = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_TOP_KARMA, null).toBoolean()
            mUserSettings.videoAutoplay = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                .getString(USER_PREFERENCES_VIDEO_AUTOPLAY, null).toBoolean()*/
            mUserSettingsAreFetched = true
        }
    }
    private fun saveUserPreferences(userSettings: UserSettings) {
        getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(USER_PREFERENCES_LANG, userSettings.lang)
            .putString(USER_PREFERENCES_ENABLE_FOLLOWERS,
                userSettings.enableFollowers.toString())
            .putString(USER_PREFERENCES_NO_PROFANITY,
                userSettings.noProfanity.toString())
            .putString(USER_PREFERENCES_HIDE_ADS,
                userSettings.hideAds.toString())
            .putString(USER_PREFERENCES_TOP_KARMA,
                userSettings.activeInCommunities.toString())
            .putString(USER_PREFERENCES_VIDEO_AUTOPLAY,
                userSettings.videoAutoplay.toString())
            .putBoolean(USER_PREFERENCES_ARE_SAVED, true)
            .apply()
    }

    override fun onClick(view: View?) {
        Toast.makeText(this, view.toString(), Toast.LENGTH_SHORT).show()
    }
}