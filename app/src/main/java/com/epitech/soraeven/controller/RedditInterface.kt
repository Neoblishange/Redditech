package com.epitech.soraeven.controller

import com.epitech.soraeven.model.AccessToken
import com.epitech.soraeven.model.DataPostResult
import com.epitech.soraeven.model.profil.UserSettings
import okhttp3.ResponseBody
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
        @Query("limit") limit: String): Call<DataPostResult?>?
    @Headers("Accept: application/json")
    @GET("/api/v1/me/prefs")
    fun getUserSettings(): Call<UserSettings>
    @Headers("Accept: application/json")
    @PATCH("/api/v1/me/prefs")
    fun setUserSettings(@Body userSettings: UserSettings): Call<UserSettings>

    @POST("/api/subscribe")
    @FormUrlEncoded
    fun subscribeOrUnsubscribeToSubreddit(
        @Field("sr_name") subredditName: String?,
        @Field("action") action: String?
    ): Call<ResponseBody?>?
}