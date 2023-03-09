package com.epitech.soraeven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class PostAndComments : AppCompatActivity() {
    private lateinit var allCommentsContainer: LinearLayout

    private lateinit var profileButton: Button
    private lateinit var homeButton: Button
    private lateinit var footerView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_and_comments)

        allCommentsContainer = findViewById(R.id.allCommentsLayout)
        displayComments(5, allCommentsContainer)

        val footer = Footer()
        footer.setupFooter(this@PostAndComments)
    }

    private fun displayComments(numberOfViews: Int, container: ViewGroup) {
        for (i in 0 until numberOfViews) {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.comment, container, false)
            container.addView(view)
        }
    }
}