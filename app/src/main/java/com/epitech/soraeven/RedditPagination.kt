package com.epitech.soraeven

import android.content.Context
import android.widget.LinearLayout
import android.widget.ScrollView

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

                        }
                        TypeOfData.SUBREDDITS_SEARCH -> {
                            val searchBar = SearchBar(context)
                            searchBar.searchSubredditBar(searchedText, count, "t5_$lastId", listener as SearchListener)
                        }
                        TypeOfData.SUBREDDIT_POSTS -> {
                            val subreddit = Subreddit()
                            subreddit.getAllSubredditPostsData(subredditUsername, scrollView.getChildAt(0) as LinearLayout, filter, count, "t3_$lastId", listener as SubredditPostsListener)
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