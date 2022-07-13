package com.demo.gitdemo.data.remote.retrofit

import okhttp3.Headers

internal object RequestHeader {
    fun get(): Headers.Builder {
        val builder = Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("Accept-Language", "en")
        return builder
    }
}