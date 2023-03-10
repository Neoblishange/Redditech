package com.epitech.soraeven

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.epitech.soraeven.controller.HomeActivity
import com.epitech.soraeven.controller.RedditClient
import com.epitech.soraeven.model.subreddit.SearchSubreddit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBar (private val context: Context){
    private lateinit var mapFilterButton: HashMap<Button, Boolean>
    private lateinit var allPostsContainer: LinearLayout

    private var limit = 7

    fun setupSearchBar(searchTextBar: EditText, container: ViewGroup, listener: SearchListener){
        var text = ""
        val delay = 1500L
        val handler = Handler()
        val runnable = Runnable {
            if(text.isNotEmpty()) {
                searchSubredditBar(container, text, 0, limit, "", listener)
                if(context is HomeActivity) {
                    context.redditPagination.loadingNewData(
                        context,
                        (container.parent as ScrollView),
                        limit,
                        TypeOfData.SUBREDDITS_SEARCH,
                        listener = apply { }
                    )
                }
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

                if(context is HomeActivity){
                    allPostsContainer = context.findViewById(R.id.allPostsLayout)
                    context.redditPagination.loadingNewData(
                        context,
                        (allPostsContainer.parent as ScrollView),
                        limit,
                        TypeOfData.HOME_POSTS,
                        listener = apply {  }
                    )
                    context.getPostsData(allPostsContainer, filter, 0, "", context.apply {  })
                }

                if(context is Subreddit){
                    allPostsContainer = context.findViewById(R.id.allPostsLayout)
                    context.redditPagination.loadingNewData(
                        context,
                        (allPostsContainer.parent as ScrollView),
                        limit,
                        TypeOfData.SUBREDDIT_POSTS,
                        listener = apply {  }
                    )
                    var redditPagination = context.redditPagination
                    redditPagination.filter = filter
                    context.getAllSubredditPostsData(
                        redditPagination.subredditUsername,
                        allPostsContainer,
                        filter,
                        0,
                        "",
                        context.apply {  })
                }
            }
        }
    }

    fun searchSubredditBar(container: ViewGroup, text: String, count: Int, limit: Int, lastId: String, listener: SearchListener){
        println("PASSE : " + text + " | " + limit + " | " + count + " | " + lastId)
        RedditClient.searchSubreddit(text, limit, count, lastId, object : Callback<SearchSubreddit?> {
            override fun onResponse(call: Call<SearchSubreddit?>, response: Response<SearchSubreddit?>) {
                val responseData: SearchSubreddit? = response.body()
                println("PASSE = " + responseData?.data?.children?.size!!)
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