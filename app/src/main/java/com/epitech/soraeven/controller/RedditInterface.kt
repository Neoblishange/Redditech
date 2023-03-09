package com.epitech.soraeven.controller

import com.epitech.soraeven.Subreddit
import com.epitech.soraeven.model.AccessToken
import com.epitech.soraeven.model.PostList
import com.epitech.soraeven.model.profil.ProfilUser
import com.epitech.soraeven.model.profil.SubredditProfilUser
import com.epitech.soraeven.model.subreddit.DisplayInfoSubreddit
import com.epitech.soraeven.model.subreddit.SearchSubreddit
import retrofit2.Call
import retrofit2.http.*

interface RedditInterface {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("access_token")
    fun getAccessToken(@Header("Authorization") credentials: String,
                       @Field("grant_type") grantType: String,
                       @Field("redirect_uri") redirectUri: String,
                       @Field("code") code: String): Call<AccessToken?>?

    @Headers("Accept: application/json")
    @GET("{filter}")
    fun getFilteredPost(
        @Path("filter") filter: String,
        @Query("limit") limit: String): Call<PostList?>?

    @Headers("Accept: application/json")
    @GET("api/v1/me")
    fun getProfile(): Call<ProfilUser?>?

    @Headers("Accept: application/json")
    @GET("subreddits/search")
    fun searchSubreddits(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String
    ): Call<SearchSubreddit?>?

    @Headers("Accept: application/json")
    @GET("{subreddit}/about")
    fun getSubredditProfile(
        @Path("subreddit") subreddit: String,
    ): Call<DisplayInfoSubreddit?>?

    @Headers("Accept: application/json")
    @GET("{subreddit}/{filter}")
    fun getSubredditPosts(
        @Path("subreddit") subreddit: String,
        @Path("filter") filter: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String
    ): Call<PostList?>?
}