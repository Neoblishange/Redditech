package com.epitech.soraeven.api.model;

import com.google.gson.annotations.SerializedName

data class DataPostResult (
    var data: DataPostList
)

data class DataPostList(
    var children: Array<ChildrenPostData>

) {
    override fun toString(): String {
        var response: String = ""
        children.forEach {
            response += it.toString()
        }
        return response
    }
}

data class ChildrenPostData(
    var id: Int,
    @SerializedName("display_name_prefixed")
    var name: String,
    var subscribers: String,
    @SerializedName("community_icon")
    var communityIcon: String,
    @SerializedName("public_description")
    var publicDescription: String

) {
    override fun toString(): String {
        return "ChildrenPostData(id=$id, name='$name', subscribers='$subscribers', communityIcon='$communityIcon', publicDescription='$publicDescription')"
    }
}