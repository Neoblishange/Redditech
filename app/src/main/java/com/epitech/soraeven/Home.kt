package com.epitech.soraeven

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.reflect.typeOf


class Home : AppCompatActivity() {
    private lateinit var profileButton: Button
    private lateinit var homeButton: Button
    private lateinit var allPostsContainer: LinearLayout
    private lateinit var homePage: ConstraintLayout

    private lateinit var searchBar: SearchBar
    private lateinit var bestButtonFilter: Button
    private lateinit var hotButtonFilter: Button
    private lateinit var newButtonFilter: Button
    private lateinit var topButtonFilter: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //filterButtons
        bestButtonFilter = findViewById(R.id.bestButtonFilter)
        hotButtonFilter = findViewById(R.id.hotButtonFilter)
        newButtonFilter = findViewById(R.id.newButtonFilter)
        topButtonFilter = findViewById(R.id.topButtonFilter)
        searchBar = SearchBar(this)
        searchBar.setupFilterButtons(
            bestButtonFilter, hotButtonFilter,
            newButtonFilter, topButtonFilter)

        profileButton = findViewById(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this@Home, Profile::class.java)
            startActivity(intent)
        }

        homeButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this@Home, Home::class.java)
            startActivity(intent)
        }

        allPostsContainer = findViewById(R.id.allPostsLayout)
        displayPost(5, allPostsContainer)

        homePage = findViewById<ConstraintLayout>(R.id.homePage)
        getViewsByTag(homePage, "community_icon")
            ?.forEach { view ->
                view.findViewById<ImageButton>(R.id.community_icon).setOnClickListener {
                    val intent = Intent(this@Home, Subreddit::class.java)
                    startActivity(intent)
                }

                view.findViewById<View>(R.id.headerPost).setOnClickListener {
                    val intent = Intent(this@Home, PostAndComments::class.java)
                    startActivity(intent)
                }
            }
    }

    private fun displayPost(numberOfViews: Int, container: ViewGroup) {
        for (i in 0 until numberOfViews) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.post, container, false)
            view.tag = "community_icon"
            container.addView(view)
        }
    }

    private fun joinSubreddit(){

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
}