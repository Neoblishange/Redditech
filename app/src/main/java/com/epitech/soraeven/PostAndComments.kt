package com.epitech.soraeven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class PostAndComments : AppCompatActivity() {
    private lateinit var allCommentsContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_and_comments)

        allCommentsContainer = findViewById(R.id.allCommentsLayout)
        displayComments(5, allCommentsContainer)
    }

    private fun displayComments(numberOfViews: Int, container: ViewGroup) {
        for (i in 0 until numberOfViews) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.comment, container, false)
            container.addView(view)
        }
    }
}