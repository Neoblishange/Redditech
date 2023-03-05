package com.epitech.soraeven.`object`.comment

data class AfficheCommentPost(
    var data: AfficheCommentPostData
)

data class AfficheCommentPostData(
    var children: ChildrenAfficheCommentPost
)

data class ChildrenAfficheCommentPost(
    var data: ChildrenAfficheCommentPostData
)

data class ChildrenAfficheCommentPostData(
  var id: Int,
  var author: String,
  var created_utc: String,
  var score: Int,
  var body: ,
  var replies: RepliesAfficheCommentPost
)

data class RepliesAfficheCommentPost(
    var data: RepliesAfficheCommentPostData
)

data class RepliesAfficheCommentPostData(
    var children: Array<ChildrenRepliesAfficheCommentPost>
)

data class ChildrenRepliesAfficheCommentPost(
    var data: ChildrenRepliesAfficheCommentPostData
)

data class ChildrenRepliesAfficheCommentPostData(
    var id: Int,
    var author: String,
    var created_utc: String,
    var score: Int,
    var body: ??,
    var replies:{etc...}
)
