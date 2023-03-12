package com.epitech.soraeven.model.profil

data class OtherUserProfile (
    var data : UserData
    ) {
    data class UserData(
        var subreddit: SubredditProfile
    ) {
        data class SubredditProfile(
            var icon_img: String
        )
    }
}