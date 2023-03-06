package com.epitech.soraeven.`object`.subreddit

data class SearchSubreddit(
    var data: DataSearchSubreddit
)

data class DataSearchSubreddit(
    var children: Array<ChildrenSearchSubreddit>
)

data class ChildrenSearchSubreddit(
    var data: ChildrenSearchSubredditData
)

data class ChildrenSearchSubredditData(
    var  id: Int,
    var  display_name_prefixed:String,
    var subscribers:Int,
    var community_icon:String,
    var public_description:String
)
