package com.epitech.soraeven.api.model

import com.google.gson.annotations.SerializedName

class AccessToken {
    @SerializedName("access_token")
    private var accessToken: String? = null

    fun getAccessToken(): String? {
        return accessToken
    }

    @SerializedName("token_type")
    private var tokenType: String? = null

    fun getTokenType(): String? {
        return tokenType
    }
}
