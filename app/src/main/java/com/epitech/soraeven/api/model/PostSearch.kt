package com.epitech.soraeven.api.model;


import com.epitech.soraeven.`object`.home.Response
import com.epitech.soraeven.`object`.home.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface PostSearch {
    companion object {
        const val filter = ""
    }
    @GET("{filter}")
    fun getPostSearchData(
        @Path("filter") filter:String
        ): Call<Response>
}
