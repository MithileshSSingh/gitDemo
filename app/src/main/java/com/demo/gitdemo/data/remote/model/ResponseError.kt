package com.demo.gitdemo.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("message") val message: String?,
    @SerializedName("documentation_url") val documentUrl: String?
)