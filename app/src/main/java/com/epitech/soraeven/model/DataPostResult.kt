package com.epitech.soraeven.model;

import com.google.gson.annotations.SerializedName

data class DataPostResult (
    var kind: String,
    var data: DataPostList
){
    data class DataPostList(
        var children: Array<ChildrenPost>

    ) {
        data class ChildrenPost(
            var data: ChildrenPostData
        ){
            data class ChildrenPostData(
                var id: String,
                var name: String,
                @SerializedName("author_fullname")
                var authorFullname: String,
                var title: String,
                var subreddit: String,
                @SerializedName("subreddit_name_prefixed")
                var subredditNamePrefixed: String,
                @SerializedName("ups")
                var numberOfUpVotes: String,
                @SerializedName("link_flair_text")
                var linkFlairText: String,
                var preview: Preview
            ){
                data class Preview(
                    var images: ArrayList<Image>,
                    var enabled: Boolean
                ){
                    data class Image (
                        var source: Source,
                        var resolutions: ArrayList<Resolution>,
                        var id: String
                    ){
                        data class Source (
                            var url: String,
                            var width: Int,
                            var height: Int
                        )
                        data class Resolution (
                            var url: String,
                            var width: Int,
                            var height: Int
                        )
                    }
                }
            }
        }
    }
}