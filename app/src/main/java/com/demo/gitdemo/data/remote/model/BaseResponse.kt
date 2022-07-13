package com.demo.gitdemo.data.remote.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
        @SerializedName("status") var status: String? = null,
        @SerializedName("message") val message: String? = null,
        @SerializedName("result") val result: T? = null
)