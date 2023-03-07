package com.epitech.soraeven

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.epitech.soraeven.controller.PostDataFilling
import com.epitech.soraeven.controller.RedditClient
import com.epitech.soraeven.model.PostList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBar (private val context: Context){
    private lateinit var mapFilterButton: HashMap<Button, Boolean>
    private lateinit var postReddit : Array<PostList.DataPostList.ChildrenPost>

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


            }
        }
    }
}