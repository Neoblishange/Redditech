package com.epitech.soraeven.controller

import android.content.Context
import com.epitech.soraeven.MyApplication
import com.epitech.soraeven.model.profil.UserSettings
import com.epitech.soraeven.Subreddit
import com.epitech.soraeven.model.PostList
import com.epitech.soraeven.model.profil.OtherUserProfile
import com.epitech.soraeven.model.profil.ProfilUser
import com.epitech.soraeven.model.subreddit.DisplayInfoSubreddit
import com.epitech.soraeven.model.subreddit.SearchSubreddit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RedditClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://oauth.reddit.com"
    private val accessTokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val accessToken = getAccessToken()
        if (accessToken != null) {
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(modifiedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(accessTokenInterceptor)
        .build()
    private fun getAccessToken(): String? {
        // Retrieve the access token from SharedPreferences
        val preferences = MyApplication.staticContext?.getSharedPreferences("my_app", Context.MODE_PRIVATE)
        return preferences?.getString("access_token", null)
    }

    private val client: RedditInterface
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RedditInterface::class.java)
        }

    fun getFilteredPost(filter: String, limit: Int, count: Int, after: String, callback: Callback<PostList?>) {
        client.getFilteredPost(filter, limit, count, after)
            ?.enqueue(callback)
    }
    fun getUserSettings(callback: Callback<UserSettings?>) {
        client.getUserSettings().enqueue(callback)
    }
    fun setUserSettings(userSettings: UserSettings, callback: Callback<UserSettings?>) {
        client.setUserSettings(userSettings).enqueue(callback)
    }
    fun subscribeOrUnsubscribeToSubreddit(srName: String?, action: String, callback: Callback<ResponseBody?>) {
        client.subscribeOrUnsubscribeToSubreddit(srName, action)?.enqueue(callback)
    }
    fun voteOnPost(name: String, dir: Int, callback: Callback<ResponseBody?>) {
        client.voteOnPost(name, dir)?.enqueue(callback)
    }

    fun getProfile(callback: Callback<ProfilUser?>){
        client.getProfile()
            ?.enqueue(callback)
    }

    fun searchSubreddit(query: String, limit: Int, count: Int, after: String, callback: Callback<SearchSubreddit?>){
        client.searchSubreddits(query, limit, count, after)
            ?.enqueue(callback)
    }

    fun getSubredditProfile(subreddit: String?, callback: Callback<DisplayInfoSubreddit?>){
        if (subreddit != null) {
            client.getSubredditProfile(subreddit)
                ?.enqueue(callback)
        }
    }

    fun getOtherUserProfile(username: String, callback: Callback<OtherUserProfile?>){
        client.getOtherUserProfile(username)
            ?.enqueue(callback)
    }

    fun getSubredditPosts(subreddit: String?, filter: String, limit: Int, count: Int, after: String, callback: Callback<PostList?>){
        if (subreddit != null) {
            client.getSubredditPosts(subreddit, filter, limit, count, after)
                ?.enqueue(callback)
        }
    }

    fun getMySubreddits(callback: Callback<SearchSubreddit?>){
        client.getMySubreddits()
            ?.enqueue(callback)
    }
}