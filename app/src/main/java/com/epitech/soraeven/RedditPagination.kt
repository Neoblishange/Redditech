package com.epitech.soraeven

import android.content.Context
import android.widget.LinearLayout
import android.widget.ScrollView
import com.epitech.soraeven.controller.HomeActivity
import com.epitech.soraeven.controller.PostsListener

class RedditPagination {
    var count: Int = 0
    var searchedText = ""
    var loadingData = false
    var lastId = ""
    var subredditUsername = ""
    var userIsSearchingSubreddit = false
    var filter = "best"

    fun loadingNewData(context: Context, scrollView: ScrollView, limit: Int, typeOfData: TypeOfData, listener: Any?) {
        scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            if(!loadingData) {
                val totalHeight = scrollView.getChildAt(0).height
                val visibleHeight = scrollView.height
                val currentPosition = scrollView.scrollY
                if (totalHeight <= currentPosition + visibleHeight) {
                    count = limit
                    loadingData = true
                    when(typeOfData){
                        TypeOfData.HOME_POSTS-> {
                            val homeActivity = HomeActivity()
                            var currentListener = if(listener is HomeActivity) {
                                listener
                            }
                            else {
                                (context as HomeActivity).apply {  }
                            }
                            homeActivity.getPostsData(
                                scrollView.getChildAt(0) as LinearLayout,
                                filter,
                                count,
                                "t3_$lastId",
                                currentListener
                            )
                        }
                        TypeOfData.SUBREDDITS_SEARCH -> {
                            val searchBar = SearchBar(context)
                            var currentListener = if(listener is HomeActivity) {
                                listener
                            }
                            else {
                                (context as HomeActivity).apply {  }
                            }
                            searchBar.searchSubredditBar((scrollView.getChildAt(0) as LinearLayout), searchedText, count, limit, "t5_$lastId", currentListener)
                        }
                        TypeOfData.SUBREDDIT_POSTS -> {
                            val subreddit = Subreddit()
                            var currentListener = if(listener is Subreddit) {
                                listener
                            }
                            else {
                                (context as Subreddit).apply {  }
                            }
                            subreddit.getAllSubredditPostsData(
                                subredditUsername,
                                scrollView.getChildAt(0) as LinearLayout,
                                filter,
                                count,
                                "t3_$lastId",
                                currentListener
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class TypeOfData(val type: String) {
    HOME_POSTS("post"),
    SUBREDDITS_SEARCH("subreddits_search"),
    SUBREDDIT_POSTS("subreddit_posts")
}