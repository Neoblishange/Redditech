package com.epitech.soraeven.api.service

import com.epitech.soraeven.api.model.AccessToken
import com.epitech.soraeven.api.model.DataPostResult
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.http.*

interface RedditClient {
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
        @Query("limit") limit: String,
        @Header("Authorization") authHeader: String): Call<DataPostResult?>?
}