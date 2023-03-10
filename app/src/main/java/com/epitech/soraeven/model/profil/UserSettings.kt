package com.epitech.soraeven.model.profil

import com.google.gson.annotations.SerializedName

data class UserSettings (
    var lang: String,
    @SerializedName("enable_followers")
    var enableFollowers: Boolean,
    @SerializedName("no_profanity")
    var noProfanity: Boolean,
    @SerializedName("hide_ads")
    var hideAds: Boolean,
    @SerializedName("top_karma_subreddits")
    var activeInCommunities: Boolean,
    @SerializedName("video_autoplay")
    var videoAutoplay: Boolean
)