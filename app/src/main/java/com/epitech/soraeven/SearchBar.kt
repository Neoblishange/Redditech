package com.epitech.soraeven

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class SearchBar (private val context: Context){
    private lateinit var mapFilterButton: HashMap<Button, Boolean>

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
            }
        }
    }
}