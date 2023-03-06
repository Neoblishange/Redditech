package com.epitech.soraeven.model.comment

data class CommentPost(
    var data: DisplayCommentPostData
)

data class DisplayCommentPostData(
    var children: ChildrenDisplayCommentPost
)

data class ChildrenDisplayCommentPost(
    var data: ChildrenDisplayCommentPostData
)

data class ChildrenDisplayCommentPostData(
  var id: Int,
  var author: String,
  var created_utc: String,
  var score: Int,
  var body: String,
  var replies: DisplayCommentPostData
)




