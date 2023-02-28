package com.epitech.soraeven.home;

import com.google.gson.annotations.SerializedName

data class DataPostResult(
    var children: Array<ChildrenPostData>
    )

data class ChildrenPostData(
    var id: Int,
    @SerializedName("display_name_prefixed")
    var name: String,
    var subscribers: String,
    var community_icon: String,
    var public_desciption: String

)
