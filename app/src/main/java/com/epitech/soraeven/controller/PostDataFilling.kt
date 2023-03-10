package com.epitech.soraeven.controller

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.PostList
import java.sql.Date
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDataFilling constructor(context: Context): View(context){
    companion object {
        fun fillPost( view: View ,data : PostList.DataPostList.ChildrenPost.ChildrenPostData?) {
            val tvSubreddit = view.findViewById<TextView>(R.id.subreddit)
            val tvAuthor = view.findViewById<TextView>(R.id.author)
            val tvCreated_utc = view.findViewById<TextView>(R.id.created_utc)
            val tvTitle = view.findViewById<TextView>(R.id.title)
            val ivThumbnail = view.findViewById<ImageView>(R.id.thumbnail)
            val tvVotes = view.findViewById<TextView>(R.id.votes)
            val tvcommentCount = view.findViewById<TextView>(R.id.commentCount)
            val upVote = view.findViewById<Button>(R.id.upVote)
            val downVote = view.findViewById<Button>(R.id.downVote)

            tvSubreddit.text = data?.subredditNamePrefixed
            val authorTextView = "By : " + data?.authorFullname
            tvAuthor.text = authorTextView
            tvCreated_utc.text = data?.created_utc.toString()
            tvTitle.text = data?.title
            tvVotes.text = data?.numberOfUpVotes.toString()
            tvcommentCount.text = data?.num_comments.toString()
            upVote.text = data?.name
            downVote.text = data?.name

            val originalVotes: Int = data?.numberOfUpVotes?.toInt()!!

            upVote.setOnClickListener {
                if (tvVotes != null) {
                    handleVoteOnPost(upVote.text as String, 1, tvVotes, originalVotes )
                }
            }

            downVote.setOnClickListener {
                if (tvVotes != null) {
                    handleVoteOnPost(downVote.text as String, -1, tvVotes, originalVotes)
                }
            }

            if (ivThumbnail != null) {
                if (data?.preview?.images?.get(0)?.source?.url != null) {
                    val baseUrlProfileImage = data?.preview?.images?.get(0)?.source?.url
                    val indexerProfileImage = baseUrlProfileImage?.indexOf("?")
                    var extractedContentImageUrl = baseUrlProfileImage
                    if (!baseUrlProfileImage?.contains("external")!!) {
                        extractedContentImageUrl = if (indexerProfileImage!! >= 0) baseUrlProfileImage.substring(0, indexerProfileImage) else baseUrlProfileImage
                        extractedContentImageUrl = extractedContentImageUrl.replace("preview", "i")
                    }
                    ivThumbnail.visibility = VISIBLE
                    val imageLoading = ImageLoading()
                    imageLoading.customViewIntegration(view.context, extractedContentImageUrl, ivThumbnail)
                }else {
                    ivThumbnail.visibility = GONE
                }
            }
        }

        private fun handleVoteOnPost(name: String, voteValue: Int, view: TextView, originNumber: Int) {
                RedditClient.voteOnPost(name, voteValue, object : Callback<ResponseBody?> {
                    override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                        view.text = (originNumber + voteValue).toString()
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    }

                })
        }

        @SuppressLint("SimpleDateFormat")
        private fun unixDateToUTC(unixDate: Long): String {
            val date = Date(unixDate * 1000)
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
            return sdf.format(date)
        }
    }
}