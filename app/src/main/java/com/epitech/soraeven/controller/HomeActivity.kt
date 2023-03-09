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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.*
import com.epitech.soraeven.model.PostList
import com.epitech.soraeven.model.subreddit.SearchSubreddit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), SearchListener  {
    private val redirectUri = Uri.parse("soraeven://oauth2redirect")
    private lateinit var profileButton: Button
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


    private lateinit var footerView: View

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
        displayPost(5, allPostsContainer, "best")

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
    private fun displayPost(numberOfViews: Int, container: ViewGroup , filter: String) {
        RedditClient.getFilteredPost(filter, 10, object : Callback<PostList?> {
            override fun onFailure(call: Call<PostList?>, t: Throwable) {
                // Handle the failure case
            }

            override fun onResponse(call: Call<PostList?>, response: Response<PostList?>) {
                // Handle the success case
                val responseData: PostList? = response.body()
                postReddit = responseData?.data?.children!!
                println(responseData?.data?.children?.size)
                for (i in 0 until postReddit?.size as Int) {
                    val view = LayoutInflater.from(container.context)
                        .inflate(R.layout.post, container, false)
                    view.tag = "community_icon"
                    container.addView(view)
                    PostDataFilling.fillPost(view, postReddit[i].data)
                }
            }
        })
    }

    private fun setListenerSearchBar(searchBar: SearchBar, container: ViewGroup){
        searchBar.setupSearchBar(searchTextBar, listener = apply {  })
    }

    private fun joinSubreddit() {

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

    //if search success
    override fun onSearchResult(searchSubreddit: SearchSubreddit) {
        resetDisplayHomeBody(allPostsContainer)
        displaySubreddit(searchSubreddit, allPostsContainer)
    }
    //cancel search
    override fun onRemoveSearch() {
        resetDisplayHomeBody(allPostsContainer)
        displayPost(5, allPostsContainer, "best")
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

    private fun resetDisplayHomeBody(view: ViewGroup){
        view.removeAllViewsInLayout()
    }
}