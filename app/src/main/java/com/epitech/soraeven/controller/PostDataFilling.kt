package com.epitech.soraeven.controller

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.epitech.soraeven.R
import com.epitech.soraeven.model.PostList

class PostDataFilling constructor(context: Context): View(context){
    companion object {
        fun fillPost( view: View ,data : PostList.DataPostList.ChildrenPost.ChildrenPostData?) {

            val tvAuthor = (view)?.findViewById<TextView>(R.id.author)
            val tvCreated_utc = (view)?.findViewById<TextView>(R.id.created_utc)
            val tvTitle = (view)?.findViewById<TextView>(R.id.title)
            val ivThumbnail = (view)?.findViewById<ImageView>(R.id.thumbnail)
            val tvVotes = (view)?.findViewById<TextView>(R.id.votes)
            val tvcommentCount = (view)?.findViewById<TextView>(R.id.commentCount)

            tvAuthor?.text = data?.authorFullname
            tvCreated_utc?.text = data?.created_utc.toString()
            tvTitle?.text = data?.title
            tvVotes?.text = data?.numberOfUpVotes.toString()
            tvcommentCount?.text = data?.num_comments.toString()

            if (ivThumbnail != null) {

                if (data?.preview?.images?.get(0)?.source?.url != null) {
                    val baseUrlProfileImage = data?.preview?.images?.get(0)?.source?.url
                    val indexerProfileImage = baseUrlProfileImage?.indexOf("?")
                    val extractedContentImageUrl = if (indexerProfileImage!! >= 0) baseUrlProfileImage.substring(0, indexerProfileImage) else baseUrlProfileImage
                    ivThumbnail.visibility = VISIBLE
                    Glide.with(view)
                        .load(extractedContentImageUrl)
                        .into(ivThumbnail)
                }else {
                    ivThumbnail.visibility = GONE
                }

            }
        }
    }
}