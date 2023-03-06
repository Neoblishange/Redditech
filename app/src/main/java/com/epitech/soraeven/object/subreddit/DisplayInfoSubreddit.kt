package com.epitech.soraeven.`object`.subreddit

data class DisplayInfoSubreddit(
    var data : DataDisplayInfo
)

data class DataDisplayInfo(
    var display_name_prefixed: String,
    var title: String,
    var community_icon: String,
    var banner_background_image: String,
    var public_description: String,
    var created_utc: String,
    var subscribers: Int,
    var accounts_active: Int
)
