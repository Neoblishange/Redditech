package com.epitech.soraeven.`object`.subreddit

data class AfficheInfoSubreddit(
    var data : DataAfficheInfo
)

data class DataAfficheInfo(
    var display_name_prefixed: String,
    var title: String,
    var community_icon: String,
    var banner_background_image: String,
    var public_description: String,
    var created_utc: String,
    var subscribers: Double,
    var accounts_active: Double
)
