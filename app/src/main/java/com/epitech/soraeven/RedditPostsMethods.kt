package com.epitech.soraeven

import android.content.Context
import android.widget.Toast
import com.epitech.soraeven.controller.RedditClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RedditPostsMethods {
    companion object {
        fun joinSubreddit(context: Context, srName: String?) {
            RedditClient.subscribeOrUnsubscribeToSubreddit(srName, "sub", object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    Toast.makeText(context, "Joined", Toast.LENGTH_SHORT)
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(context, "Failed to join", Toast.LENGTH_SHORT)
                }

            })
        }

        fun leaveSubreddit(context: Context, srName: String?) {
            RedditClient.subscribeOrUnsubscribeToSubreddit(srName, "unsub", object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    Toast.makeText(context, "Leaved", Toast.LENGTH_SHORT)
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(context, "Failed to leave", Toast.LENGTH_SHORT)
                }

            })
        }
    }
}