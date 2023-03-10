package com.epitech.soraeven

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.controller.PostDataFilling
import com.epitech.soraeven.controller.RedditClient
import com.epitech.soraeven.controller.SubredditDataFilling
import com.epitech.soraeven.model.PostList
import com.epitech.soraeven.model.subreddit.DisplayInfoSubreddit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Subreddit : AppCompatActivity(), SubredditPostsListener {
    private lateinit var allPostsContainer: LinearLayout
    private lateinit var searchBar: SearchBar
    private lateinit var bestButtonFilter: Button
    private lateinit var hotButtonFilter: Button
    private lateinit var newButtonFilter: Button
    private lateinit var topButtonFilter: Button

    var redditPagination = RedditPagination()
    var limit = 6
    var maxDisplayPosts = limit * 2

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subreddit)

        //filterButtons
        bestButtonFilter = findViewById(R.id.bestButtonFilter)
        hotButtonFilter = findViewById(R.id.hotButtonFilter)
        newButtonFilter = findViewById(R.id.newButtonFilter)
        topButtonFilter = findViewById(R.id.topButtonFilter)
        searchBar = SearchBar(this)
        searchBar.setupFilterButtons(
            bestButtonFilter, hotButtonFilter,
            newButtonFilter, topButtonFilter)

        val footer = Footer()
        footer.setupFooter(this@Subreddit)

        allPostsContainer = findViewById(R.id.allPostsLayout)


        //username subreddit
        val subredditUsername = intent.getStringExtra("subredditUsername")

        getAllSubredditPostsData(subredditUsername, allPostsContainer, "best", 0, "", listener = apply {  })

        RedditClient.getSubredditProfile(subredditUsername, object : Callback<DisplayInfoSubreddit?> {
            override fun onResponse(call: Call<DisplayInfoSubreddit?>, response: Response<DisplayInfoSubreddit?>) {
                val responseData: DisplayInfoSubreddit? = response.body()
                SubredditDataFilling.fillSubredditProfile(findViewById(R.id.headerPost), responseData?.data)
            }

            override fun onFailure(call: Call<DisplayInfoSubreddit?>, t: Throwable) {

            }

        })
        redditPagination.loadingNewData(applicationContext, allPostsContainer.parent as ScrollView, limit, TypeOfData.SUBREDDIT_POSTS, listener = apply {  })
    }

    fun getAllSubredditPostsData(subredditUsername: String?, container: ViewGroup, filter: String, count: Int, lastId: String, listener: SubredditPostsListener) {
        if(count == 0 && lastId.isEmpty()){
            maxDisplayPosts = 0
        }
        RedditClient.getSubredditPosts(subredditUsername, filter, limit, count, lastId, object : Callback<PostList?> {
            override fun onFailure(call: Call<PostList?>, t: Throwable) {
                // Handle the failure case
            }

            override fun onResponse(call: Call<PostList?>, response: Response<PostList?>) {
                // Handle the success case
                val responseData: PostList? = response.body()
                responseData?.let { data ->
                    listener.onResult(subredditUsername, data)
                }
            }
        })
    }

    fun displaySubredditPosts(container: ViewGroup, subredditPostsData: PostList){
        for (i in 0 until subredditPostsData.data.children.size) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.post, container, false)
            view.tag = "community_icon"
            container.addView(view)
            PostDataFilling.fillPost(view, subredditPostsData.data.children[i].data)
        }
    }

    override fun onResult(subredditUsername: String?, subredditPostsData: PostList) {
        if(maxDisplayPosts == 0){
            resetScrollViewDisplay(allPostsContainer)
            maxDisplayPosts = limit * 2
        }
        maxDisplayPosts -= limit
        displaySubredditPosts(allPostsContainer, subredditPostsData)
        redditPagination.subredditUsername = subredditUsername!!
        redditPagination.lastId = subredditPostsData.data.children[subredditPostsData.data.children.size - 1].data.id
        redditPagination.loadingData = false
    }

    private fun resetScrollViewDisplay(view: ViewGroup){
        view.removeAllViewsInLayout()
        (view.parent as ScrollView).scrollY = 0
    }
}

interface SubredditPostsListener {
    fun onResult(subredditUsername: String?, subredditPostsData: PostList)
}