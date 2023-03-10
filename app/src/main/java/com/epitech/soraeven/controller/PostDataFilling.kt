package com.epitech.soraeven.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.RedditPostsMethods
import com.epitech.soraeven.Subreddit
import com.epitech.soraeven.model.PostList
import java.sql.Date
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDataFilling constructor(context: Context): View(context){
    companion object {
        fun fillPost(view: View, data : PostList.DataPostList.ChildrenPost.ChildrenPostData?) {
            val tvSubreddit = view.findViewById<TextView>(R.id.subreddit)
            val tvAuthor = view.findViewById<TextView>(R.id.author)
            val tvCreated_utc = view.findViewById<TextView>(R.id.created_utc)
            val tvTitle = view.findViewById<TextView>(R.id.title)
            val ivThumbnail = view.findViewById<ImageView>(R.id.thumbnail)
            val tvVotes = view.findViewById<TextView>(R.id.votes)
            val tvcommentCount = view.findViewById<TextView>(R.id.commentCount)
            val upVote = view.findViewById<Button>(R.id.upVote)
            val downVote = view.findViewById<Button>(R.id.downVote)
            val joinButton = view.findViewById<Button>(R.id.join)

            tvSubreddit.text = data?.subredditNamePrefixed
            val authorTextView = "By : " + data?.authorFullname
            tvAuthor.text = authorTextView
            tvCreated_utc.text = data?.created_utc?.toLong()?.let { unixDateToUTC(it) }
            tvTitle.text = data?.title
            tvVotes.text = data?.numberOfUpVotes.toString()
            tvcommentCount.text = data?.num_comments.toString()
            upVote.text = data?.name
            downVote.text = data?.name

            val originalVotes: Int = data?.numberOfUpVotes?.toInt()!!

            val postVote = data.likes
            if(postVote == "true"){
                upVote.setBackgroundResource(R.drawable.filled_upchevron)
                upVote.tag = true
                downVote.tag = false
            }
            else if(postVote == "false") {
                downVote.setBackgroundResource(R.drawable.filled_downchevron)
                upVote.tag = false
                downVote.tag = true
            }
            else {
                upVote.setBackgroundResource(R.drawable.upchevron)
                downVote.setBackgroundResource(R.drawable.downchevron)
                upVote.tag = false
                downVote.tag = false
            }

            upVote.setOnClickListener {
                if (tvVotes != null) {
                    if(upVote.tag == false) {
                        handleVoteOnPost(upVote.text as String, 1, tvVotes, originalVotes)
                        upVote.setBackgroundResource(R.drawable.filled_upchevron)
                        downVote.setBackgroundResource(R.drawable.downchevron)
                        upVote.tag = true
                        downVote.tag = false
                    }
                    else {
                        handleVoteOnPost(upVote.text as String, 0, tvVotes, originalVotes)
                        upVote.setBackgroundResource(R.drawable.upchevron)
                        upVote.tag = false
                        downVote.tag = false
                    }
                }
            }

            downVote.setOnClickListener {
                if (tvVotes != null) {
                    if(downVote.tag == false) {
                        handleVoteOnPost(downVote.text as String, -1, tvVotes, originalVotes)
                        upVote.setBackgroundResource(R.drawable.upchevron)
                        downVote.setBackgroundResource(R.drawable.filled_downchevron)
                        upVote.tag = false
                        downVote.tag = true
                    }
                    else {
                        handleVoteOnPost(downVote.text as String, 0, tvVotes, originalVotes)
                        downVote.setBackgroundResource(R.drawable.downchevron)
                        upVote.tag = false
                        downVote.tag = false
                    }
                }
            }

            if(view.context is Subreddit || view.context is HomeActivity) {
                val parent = joinButton.parent as ViewGroup
                parent.removeView(joinButton)
            }

            if (ivThumbnail != null) {

                if (data?.preview?.images?.get(0)?.source?.url != null) {
                    val baseUrlProfileImage = data?.preview?.images?.get(0)?.source?.url
                    val indexerProfileImage = baseUrlProfileImage?.indexOf("?")
                    var extractedContentImageUrl = if (indexerProfileImage!! >= 0) baseUrlProfileImage.substring(0, indexerProfileImage) else baseUrlProfileImage
                    if(extractedContentImageUrl.contains("external")) {
                        extractedContentImageUrl = extractedContentImageUrl.replace("external-preview", "i")
                    }
                    else {
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