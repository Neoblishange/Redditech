package com.epitech.soraeven.controller

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.*
import com.epitech.soraeven.model.PostList
import okhttp3.ResponseBody
import com.epitech.soraeven.model.subreddit.SearchSubreddit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), SearchListener, PostsListener  {
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private lateinit var homeButton: Button
    private lateinit var allPostsContainer: LinearLayout
    private lateinit var homePage: ConstraintLayout

    private lateinit var searchBar: SearchBar
    private lateinit var searchTextBar: EditText
    private lateinit var bestButtonFilter: Button
    private lateinit var hotButtonFilter: Button
    private lateinit var newButtonFilter: Button
    private lateinit var topButtonFilter: Button

    private lateinit var postReddit : Array<PostList.DataPostList.ChildrenPost>

    var redditPagination = RedditPagination()
    private var limit = 6
    private var maxDisplayPosts = limit * 2

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //filterButtons
        bestButtonFilter = findViewById(R.id.bestButtonFilter)
        hotButtonFilter = findViewById(R.id.hotButtonFilter)
        newButtonFilter = findViewById(R.id.newButtonFilter)
        topButtonFilter = findViewById(R.id.topButtonFilter)
        searchTextBar = findViewById(R.id.searchTextBar)
        searchBar = SearchBar(this)
        searchBar.setupFilterButtons(
            bestButtonFilter, hotButtonFilter,
            newButtonFilter, topButtonFilter)

        val footer = Footer()
        footer.setupFooter(this@HomeActivity)

        homeButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        allPostsContainer = findViewById(R.id.allPostsLayout)
        getPostsData(allPostsContainer, "best", 0, "", listener = apply {  })

        homePage = findViewById<ConstraintLayout>(R.id.homePage)
        getViewsByTag(homePage, "community_icon")
            ?.forEach { view ->
                view.findViewById<ImageButton>(R.id.community_icon).setOnClickListener {
                    val intent = Intent(this@HomeActivity, Subreddit::class.java)
                    startActivity(intent)
                }

                view.findViewById<View>(R.id.headerPost).setOnClickListener {
                    val intent = Intent(this@HomeActivity, PostAndComments::class.java)
                    startActivity(intent)
                }
            }

        setListenerSearchBar(searchBar, allPostsContainer)
        redditPagination.loadingNewData(
            applicationContext,
            (allPostsContainer.parent as ScrollView),
            limit,
            TypeOfData.HOME_POSTS,
            listener = apply {  }
        )
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(redirectUri.toString())) {
            val code = uri.getQueryParameter("code")
            val authenticator = RedditAuthenticator(this)
            if (code != null) {
                authenticator.authenticate(code)
            }
        }
    }
    fun getPostsData(container: ViewGroup, filter: String, count: Int, lastId: String, listener: PostsListener) {
        if(count == 0 && lastId.isEmpty()){
            maxDisplayPosts = 0
        }
        RedditClient.getFilteredPost(filter, limit, count, lastId, object : Callback<PostList?> {
            override fun onFailure(call: Call<PostList?>, t: Throwable) {
                // Handle the failure case
            }

            override fun onResponse(call: Call<PostList?>, response: Response<PostList?>) {
                // Handle the success case
                val responseData: PostList? = response.body()
                responseData?.let { data ->
                    listener.onResult(data)
                }
            }
        })
    }

    override fun onResult(postsData: PostList) {
        if(maxDisplayPosts == 0){
            resetScrollViewDisplay(allPostsContainer)
            maxDisplayPosts = limit * 2
        }
        maxDisplayPosts -= limit
        displayPosts(allPostsContainer, postsData)
        redditPagination.lastId = postsData.data.children[postsData.data.children.size - 1].data.id
        redditPagination.loadingData = false
    }

    private fun displayPosts(container: ViewGroup, postsData: PostList){
        val postReddit = postsData?.data?.children!!
        for (i in 0 until postReddit.size) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.post, container, false)
            view.tag = "community_icon"
            PostDataFilling.fillPost(view, postReddit[i].data)
            container.addView(view)
        }
    }

    private fun setListenerSearchBar(searchBar: SearchBar, container: ViewGroup){
        searchBar.setupSearchBar(searchTextBar, container, listener = apply {  })
    }

    private fun handleJoinButton() {
        // If the user has already joined the community, he left it and vice versa
        // Need the community status
    }
    private fun joinSubreddit(srName: String/*, communityIcon: ImageButton */) {
        RedditClient.subscribeOrUnsubscribeToSubreddit(srName, "sub", object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(this@HomeActivity, "Joined", Toast.LENGTH_SHORT)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Failed to join", Toast.LENGTH_SHORT)
            }

        })
    }

    private fun leaveSubreddit(srName: String/*, communityIcon: ImageButton */) {
        RedditClient.subscribeOrUnsubscribeToSubreddit(srName, "unsub", object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(this@HomeActivity, "Leaved", Toast.LENGTH_SHORT)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Failed to leave", Toast.LENGTH_SHORT)
            }

        })
    }

    private fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View>? {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                views.addAll(getViewsByTag(child, tag)!!)
            }
            val tagObj = child.tag
            if (tagObj != null && tagObj == tag) {
                views.add(child)
            }
        }
        return views
    }

    companion object {
        fun getPostsData(i: Int, allPostsContainer: LinearLayout?, s: String) {

        }
    }

    //if search success
    override fun onSearchResult(text: String, searchSubreddit: SearchSubreddit) {
        if(!redditPagination.userIsSearchingSubreddit) {
            resetScrollViewDisplay(allPostsContainer)
            redditPagination.userIsSearchingSubreddit = true
        }
        maxDisplayPosts -= limit
        if(maxDisplayPosts == 0){
            resetScrollViewDisplay(allPostsContainer)
            maxDisplayPosts = limit * 2
        }
        displaySubreddit(searchSubreddit, allPostsContainer)
        redditPagination.searchedText = text
        redditPagination.loadingData = false
        redditPagination.lastId = searchSubreddit.data.children[searchSubreddit.data.children.size - 1].data.id
    }
    //cancel search
    override fun onRemoveSearch() {
        resetScrollViewDisplay(allPostsContainer)
        getPostsData(allPostsContainer, "best", 0, "", listener = apply {  })
        redditPagination.userIsSearchingSubreddit = false
    }

    private fun displaySubreddit(searchSubreddit: SearchSubreddit, container: ViewGroup){
        for(i in 0 until searchSubreddit.data.children.size) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.subreddit_profile, container, false)
            container.addView(view)
            //view.tag = "subreddit" + searchSubreddit.data.children[i].data.id
            view.setOnClickListener {
                val intent = Intent(this@HomeActivity, Subreddit::class.java)
                intent.putExtra("subredditUsername", searchSubreddit.data.children[i].data.display_name_prefixed)
                startActivity(intent)
            }
            SubredditDataFilling.fillAllSubreddits(view, searchSubreddit.data.children[i].data)
        }
    }

    private fun resetScrollViewDisplay(view: ViewGroup){
        view.removeAllViewsInLayout()
        (view.parent as ScrollView).scrollY = 0
    }
}

interface PostsListener {
    fun onResult(postsData: PostList)
}