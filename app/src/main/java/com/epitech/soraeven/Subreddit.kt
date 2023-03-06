package com.epitech.soraeven

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.epitech.soraeven.controller.HomeActivity

class Subreddit : AppCompatActivity() {
    private lateinit var homeButton: Button
    private lateinit var allPostsContainer: LinearLayout

    private lateinit var searchBar: SearchBar
    private lateinit var bestButtonFilter: Button
    private lateinit var hotButtonFilter: Button
    private lateinit var newButtonFilter: Button
    private lateinit var topButtonFilter: Button

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

        homeButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this@Subreddit, HomeActivity::class.java)
            startActivity(intent)
        }

        allPostsContainer = findViewById(R.id.allPostsLayout)
        displayPosts(5, allPostsContainer)
    }

    private fun displayPosts(numberOfViews: Int, container: ViewGroup) {
        for (i in 0 until numberOfViews) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.post, container, false)
            view.tag = "community_icon"
            container.addView(view)
        }
    }
}