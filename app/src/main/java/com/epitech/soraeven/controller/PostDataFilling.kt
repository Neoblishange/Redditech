package com.epitech.soraeven.controller

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.PostList
import java.sql.Date

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
            tvCreated_utc?.text = data?.created_utc?.let { unixDateToUTC(it.toLong()) }
            tvTitle?.text = data?.title
            tvVotes?.text = data?.numberOfUpVotes.toString()
            tvcommentCount?.text = data?.num_comments.toString()

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
                    /*Glide.with(view)
                        .load(extractedContentImageUrl)
                        .into(ivThumbnail)*/
                }else {
                    ivThumbnail.visibility = GONE
                }

            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun unixDateToUTC(unixDate: Long): String {
            val date = Date(unixDate * 1000)
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
            return sdf.format(date)
        }
    }
}