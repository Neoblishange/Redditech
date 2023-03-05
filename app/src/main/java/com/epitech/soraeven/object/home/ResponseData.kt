package com.epitech.soraeven.`object`.home;

import com.google.gson.annotations.SerializedName

data class Response (
    var data: DataPostResult
            )
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
