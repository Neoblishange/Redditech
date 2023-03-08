package com.epitech.soraeven.controller

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.model.PostList
import com.epitech.soraeven.model.subreddit.SearchSubreddit

class SubredditDataFilling constructor(context: Context): View(context){
    companion object {
        fun fillPost(view: View, data : SearchSubreddit.DataSearchSubreddit.ChildrenSearchSubreddit.ChildrenSearchSubredditData?) {
            val subredditName = view.findViewById<TextView>(R.id.subredditName)
            val subredditPrefixedName = view.findViewById<TextView>(R.id.subredditPrefixedName)
            val subscribers = view.findViewById<TextView>(R.id.subscribers)
            val public_description = view.findViewById<TextView>(R.id.public_description)
            val community_icon = view.findViewById<ImageView>(R.id.community_icon)

            subredditName?.text = data?.display_name_prefixed
            subredditPrefixedName?.text = data?.display_name_prefixed
            subscribers?.text = formatNumber(data?.subscribers)
            public_description?.text = data?.public_description

            val imageLoading = ImageLoading()
            imageLoading.simpleImageViewIntegration(view.context, data?.community_icon, community_icon)
        }

        fun formatNumber(num: Int?): String {
            if (num != null) {
                return if (num >= 1000) {
                    val newNum = num / 1000
                    "$newNum k members"
                } else {
                    num.toString()
                }
            }
            return "0k members"
        }
    }
}