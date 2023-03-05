package com.epitech.soraeven.`object`.subreddit

data class AffichePostSubreddit(
    var data: DataAffichePost
)

data class DataAffichePost(
    var children: Array<ChildrenAffichePostData>
)

data class ChildrenAffichePostData(
    var id:Int,
    var subreddit_name_prefixed:String,
    var title:String,
    var author:String,
    var thumbnail:String,
    var ups: Double,
    var num_comments:Double
)