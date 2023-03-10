package com.epitech.soraeven.controller

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.epitech.soraeven.ImageLoading
import com.epitech.soraeven.R
import com.epitech.soraeven.RedditPostsMethods
import com.epitech.soraeven.model.subreddit.DisplayInfoSubreddit
import com.epitech.soraeven.model.subreddit.SearchSubreddit

class SubredditDataFilling constructor(context: Context): View(context){
    companion object {
        fun fillAllSubreddits(view: View, data : SearchSubreddit.DataSearchSubreddit.ChildrenSearchSubreddit.ChildrenSearchSubredditData?) {
            val subredditName = view.findViewById<TextView>(R.id.subredditName)
            val subredditPrefixedName = view.findViewById<TextView>(R.id.subredditPrefixedName)
            val subscribers = view.findViewById<TextView>(R.id.subscribers)
            val public_description = view.findViewById<TextView>(R.id.public_description)
            val community_icon = view.findViewById<ImageView>(R.id.community_icon)
            val joinSubreddit = view.findViewById<Button>(R.id.join)
            joinSubreddit.tag = data?.display_name_prefixed

            subredditName?.text = data?.display_name_prefixed
            subredditPrefixedName?.text = data?.display_name_prefixed
            subscribers?.text = formatNumber(data?.subscribers)
            public_description?.text = data?.public_description

            val imageLoading = ImageLoading()
            imageLoading.simpleImageViewIntegration(view.context, data?.community_icon, community_icon)

            if (HomeActivity.mySubreddits.contains(data?.display_name_prefixed)) {
                onJoinSubreddit(view.parent as ViewGroup, data?.display_name_prefixed!!)
            } else {
                onLeaveSubreddit(view.parent as ViewGroup, data?.display_name_prefixed!!)
            }
        }

        fun fillSubredditProfile(view: View, data : DisplayInfoSubreddit.DataDisplayInfo?){
            val subredditName = view.findViewById<TextView>(R.id.subredditName)
            val subredditPrefixedName = view.findViewById<TextView>(R.id.subredditPrefixedName)
            val subscribers = view.findViewById<TextView>(R.id.subscribers)
            val public_description = view.findViewById<TextView>(R.id.public_description)
            val community_icon = view.findViewById<ImageView>(R.id.community_icon)
            val joinSubreddit  = view.findViewById<Button>(R.id.join)
            joinSubreddit.tag = data?.display_name_prefixed

            subredditName?.text = data?.display_name_prefixed
            subredditPrefixedName?.text = data?.display_name_prefixed
            subscribers?.text = formatNumber(data?.subscribers)
            public_description?.text = data?.public_description

            val imageLoading = ImageLoading()
            imageLoading.simpleImageViewIntegration(view.context, data?.community_icon, community_icon)

            if (HomeActivity.mySubreddits.contains(data?.display_name_prefixed)) {
                onJoinSubreddit(view.parent as ViewGroup, data?.display_name_prefixed!!)
            } else {
                onLeaveSubreddit(view.parent as ViewGroup, data?.display_name_prefixed!!)
            }
        }

        fun onJoinSubreddit(view: View, subredditUsername: String){
            val joinButton = view.findViewWithTag<Button>(subredditUsername)
            joinButton.text = "LEAVE"
            joinButton.setOnClickListener {
                RedditPostsMethods.leaveSubreddit(view.context, subredditUsername)
                HomeActivity.mySubreddits.remove(subredditUsername)
                onLeaveSubreddit(view, subredditUsername)
            }
        }

        fun onLeaveSubreddit(view: View, subredditUsername: String){
            val joinButton = view.findViewWithTag<Button>(subredditUsername)
            joinButton.text = "JOIN"
            joinButton.setOnClickListener {
                RedditPostsMethods.joinSubreddit(view.context, subredditUsername)
                HomeActivity.mySubreddits.add(subredditUsername)
                onJoinSubreddit(view, subredditUsername)
            }
        }

        fun formatNumber(num: Int?): String {
            if (num != null) {
                return if (num >= 1000) {
                    val newNum = num / 1000
                    "$newNum" + "k members"
                } else {
                    return "$num members"
                }
            }
            return "0k members"
        }
    }
}