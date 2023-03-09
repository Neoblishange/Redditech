package com.epitech.soraeven.model.profil

data class ProfilUser(
    var name: String,
    var subreddit: SubredditProfilUser,
    var created_utc: Int
)

data class SubredditProfilUser(
    var subreddit_type: String,
    var title: String,
    var public_description: String,
    var banner_img: String,
    var icon_img: String
)
