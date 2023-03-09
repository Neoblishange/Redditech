package com.epitech.soraeven.controller

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.profil.UserSettings
import com.epitech.soraeven.model.profil.ProfilUser
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
    private val languages = listOf("English", "French", "German", "Spanish", "Italian")

    private lateinit var mDisplayLanguage: TextView
    private lateinit var mEnableFollowers: SwitchCompat
    private lateinit var mNoProfanity: SwitchCompat
    private lateinit var mHideAds: SwitchCompat
    private lateinit var mActiveInCommunities: SwitchCompat
    private lateinit var mAutoplayVideos: SwitchCompat

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
            }
            override fun onResponse(call: Call<UserSettings?>, response: Response<UserSettings?>) {
                val responseUserSettings = response.body()
                responseUserSettings?.let { saveUserPreferences(it) }
                if (responseUserSettings != null) {
                    setUserSettings(responseUserSettings)
                    initializeMUserSettings(responseUserSettings)
                }
            }
        })
    }

    private fun initializeMUserSettings(userSettings: UserSettings) {
        mUserSettings = UserSettings(
            userSettings.lang,
            userSettings.enableFollowers,
            userSettings.noProfanity,
            userSettings.hideAds,
            userSettings.activeInCommunities,
            userSettings.videoAutoplay
        )
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
                    .getBoolean(USER_PREFERENCES_ENABLE_FOLLOWERS, false),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getBoolean(USER_PREFERENCES_NO_PROFANITY, false),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getBoolean(USER_PREFERENCES_HIDE_ADS, false),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getBoolean(USER_PREFERENCES_TOP_KARMA, false),
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
                    .getBoolean(USER_PREFERENCES_VIDEO_AUTOPLAY, false)
            )
            mUserSettingsAreFetched = true
        }
    }
    private fun saveUserPreferences(userSettings: UserSettings) {
        getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(USER_PREFERENCES_LANG, userSettings.lang)
            .putBoolean(USER_PREFERENCES_ENABLE_FOLLOWERS,
                userSettings.enableFollowers)
            .putBoolean(USER_PREFERENCES_NO_PROFANITY,
                userSettings.noProfanity)
            .putBoolean(USER_PREFERENCES_HIDE_ADS,
                userSettings.hideAds)
            .putBoolean(USER_PREFERENCES_TOP_KARMA,
                userSettings.activeInCommunities)
            .putBoolean(USER_PREFERENCES_VIDEO_AUTOPLAY,
                userSettings.videoAutoplay)
            .putBoolean(USER_PREFERENCES_ARE_SAVED, true)
            .apply()
    }
    override fun onClick(view: View?) {
        var shouldTriggerUpdateUserSettings = true

        if (view == mDisplayLanguage) {
            shouldTriggerUpdateUserSettings = false
            handleLanguageUpdate()
        }
        when (view) {
            mEnableFollowers -> {
                updateUserBooleanPreference(mEnableFollowers, USER_PREFERENCES_ENABLE_FOLLOWERS)
                mUserSettings?.enableFollowers = mEnableFollowers.isChecked
            }
            mNoProfanity -> {
                updateUserBooleanPreference(mNoProfanity, USER_PREFERENCES_NO_PROFANITY)
                mUserSettings?.noProfanity = mNoProfanity.isChecked
            }
            mHideAds -> {
                updateUserBooleanPreference(mHideAds, USER_PREFERENCES_HIDE_ADS)
                mUserSettings?.hideAds = mHideAds.isChecked
            }
            mActiveInCommunities -> {
                updateUserBooleanPreference(mActiveInCommunities, USER_PREFERENCES_TOP_KARMA)
                mUserSettings?.activeInCommunities = mActiveInCommunities.isChecked
            }
            mAutoplayVideos -> {
                updateUserBooleanPreference(mAutoplayVideos, USER_PREFERENCES_VIDEO_AUTOPLAY)
                mUserSettings?.videoAutoplay = mAutoplayVideos.isChecked
            }
        }
        if (shouldTriggerUpdateUserSettings) updateUserSettings()
    }

    private fun handleLanguageUpdate() {
        val popupMenu = PopupMenu(this@UserSettingsActivity, mDisplayLanguage)
        languages.forEach { lang ->
            popupMenu.menu.add(lang)
        }
        popupMenu.setOnMenuItemClickListener { menuItem ->
            mDisplayLanguage.text = menuItem.title
            when (menuItem.title) {
                "English" -> mUserSettings?.lang = "en"
                "French" -> mUserSettings?.lang = "fr"
                "German" -> mUserSettings?.lang = "de"
                "Spanish" -> mUserSettings?.lang = "es"
                "Italian" -> mUserSettings?.lang = "it"
            }
            updateUserStringPreference(mUserSettings?.lang, USER_PREFERENCES_LANG)
            updateUserSettings()
            true
        }
        popupMenu.show()
    }

    private fun updateUserStringPreference(userStringPreference: String?, userPreference: String) {
        getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(userPreference, userStringPreference)
            .apply()
    }

    private fun updateUserBooleanPreference(switch: SwitchCompat, userPreference: String) {
        getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(userPreference, switch.isChecked)
            .apply()
    }

    private fun updateUserSettings() {
        mUserSettings?.let {
            RedditClient.setUserSettings(it, object : Callback<UserSettings?> {
                override fun onResponse(
                    call: Call<UserSettings?>,
                    response: Response<UserSettings?>
                ) {
                    println(response)
                }
                override fun onFailure(call: Call<UserSettings?>, t: Throwable) {
                }

            })
        }
    }
}