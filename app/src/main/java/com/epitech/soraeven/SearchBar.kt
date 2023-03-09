package com.epitech.soraeven

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.LinearLayout
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.epitech.soraeven.controller.PostDataFilling
import com.epitech.soraeven.controller.RedditClient
import com.epitech.soraeven.model.PostList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.epitech.soraeven.model.subreddit.SearchSubreddit

class SearchBar (private val context: Context){
    private lateinit var mapFilterButton: HashMap<Button, Boolean>
    private lateinit var postReddit : Array<PostList.DataPostList.ChildrenPost>
    private lateinit var allPostsContainer: LinearLayout

    private fun displayPostButton(numberOfViews: Int, container: ViewGroup, filter: String) {
        container.removeAllViews()
        RedditClient.getFilteredPost(filter, 10, object : Callback<PostList?> {
            override fun onFailure(call: Call<PostList?>, t: Throwable) {
                // Handle the failure case
            }

            override fun onResponse(call: Call<PostList?>, response: Response<PostList?>) {
                // Handle the success case
                val responseData: PostList? = response.body()
                postReddit = responseData?.data?.children!!
                for (i in 0 until postReddit?.size as Int) {
                    val view = LayoutInflater.from(container.context)
                        .inflate(R.layout.post, container, false)
                    view.tag = "community_icon"
                    PostDataFilling.fillPost(view, postReddit[i].data)
                    container.addView(view)

                }
            }
        })
    }


    fun setupSearchBar(searchTextBar: EditText, listener: SearchListener){
        var text = ""
        val delay = 1500L
        val handler = Handler()
        val runnable = Runnable {
            if(text.isNotEmpty()) {
                searchSubredditBar(text, 0, "", listener)
            }
            else {
                listener.onRemoveSearch()
            }
        }

        searchTextBar.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                text = s.toString()
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, delay)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setupFilterButtons(bestButtonFilter: Button,
                           hotButtonFilter: Button,
                           newButtonFilter: Button,
                           topButtonFilter: Button) {
        mapFilterButton = hashMapOf<Button, Boolean>(
            bestButtonFilter to true,
            hotButtonFilter to false,
            newButtonFilter to false,
            topButtonFilter to false
        )

        mapFilterButton.forEach { buttonFilter ->
            buttonFilter.key.setOnClickListener {
                mapFilterButton[buttonFilter.key] = true
                mapFilterButton.keys.filter { it != buttonFilter.key }
                    .forEach {
                        println("COLOR : " + it)
                        mapFilterButton[it] = false
                        it.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(
                                context, R.color.white
                            ))
                    }
                buttonFilter.key.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(
                        context, R.color.darkOrange
                    ))
                var filter = "best"

                when (buttonFilter.key) {
                    bestButtonFilter -> filter = "best"
                    hotButtonFilter -> filter = "hot"
                    newButtonFilter -> filter = "new"
                    topButtonFilter -> filter = "top"
                    else -> filter = "best"
                }

                allPostsContainer = (context as Activity).findViewById(R.id.allPostsLayout)
                displayPostButton(10, allPostsContainer, filter)

            }
        }
    }

    fun searchSubredditBar(text: String, count: Int, lastId: String, listener: SearchListener){
        RedditClient.searchSubreddit(text, 10, count, lastId, object : Callback<SearchSubreddit?> {
            override fun onResponse(call: Call<SearchSubreddit?>, response: Response<SearchSubreddit?>) {
                val responseData: SearchSubreddit? = response.body()
                responseData?.let { data ->
                    listener.onSearchResult(text, data)
                }
            }

            override fun onFailure(call: Call<SearchSubreddit?>, t: Throwable) {

            }
        })
    }
}

interface SearchListener {
    fun onSearchResult(text: String, subreddits: SearchSubreddit)
    fun onRemoveSearch()
}