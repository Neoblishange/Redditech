package com.epitech.soraeven

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.epitech.soraeven.controller.HomeActivity
import com.epitech.soraeven.controller.ProfileActivity

class Footer {
    private lateinit var profileButton: Button
    private lateinit var homeButton: Button

    fun setupFooter(context: Context){
        val footerView = (context as? Activity)?.findViewById<ConstraintLayout>(R.id.footer)
        if (footerView != null) {
            profileButton = footerView.findViewById(R.id.profileButton)
            if(context.javaClass.simpleName != "ProfileActivity") {
                profileButton.setOnClickListener {
                    val intent = Intent(context.applicationContext, ProfileActivity::class.java)
                    context.startActivity(intent)
                }
            }
            homeButton = footerView.findViewById(R.id.homeButton)
            if(context.javaClass.simpleName != "HomeActivity") {
                homeButton.setOnClickListener {
                    val intent = Intent(context.applicationContext, HomeActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}