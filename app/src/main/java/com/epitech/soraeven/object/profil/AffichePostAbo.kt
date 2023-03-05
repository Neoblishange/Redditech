package com.epitech.soraeven.`object`.profil

data class AffichePostAbo(
    var data: DataAffichePostAbo
)

data class DataAffichePostAbo(
    var children: Array<ChildrenAffichePostAbo>
)

data class ChildrenAffichePostAbo(
    var data: ChildrenAffichePostAboData
)

data class ChildrenAffichePostAboData(
    var id: Int,
    var subreddit_name_prefixed:String,
    var title:String,
    var author:String,
    var thumbnail:String,
    var num_comments:Int
)
