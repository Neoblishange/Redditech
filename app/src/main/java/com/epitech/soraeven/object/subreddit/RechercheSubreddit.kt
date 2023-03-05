package com.epitech.soraeven.`object`.subreddit

data class RechercheSubreddit(
    var data: DataRechercheSubreddit
)

data class DataRechercheSubreddit(
    var children: Array<ChildrenRechercheSubreddit>
)

data class ChildrenRechercheSubreddit(
  var  id: Int,
var  display_name_prefixed:String,
var subscribers:Int,
var community_icon:String,
var public_description:String
)
