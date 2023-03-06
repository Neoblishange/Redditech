package com.epitech.soraeven.`object`.profil

data class ProfilUser(
    var name: String,
    var subreddit: SubredditProfilUser
)

data class SubredditProfilUser(
    var subreddit_type: String,
    var title: String,
    var public_description: String,
    var banner_img: String,
    var icon_img: String,
    var created_utc: Int
)
