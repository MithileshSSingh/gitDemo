package com.demo.gitdemo.data.remote.retrofit

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpProvider {
    private var instance: OkHttpClient? = null

    fun get(): OkHttpClient {
        return instance ?: synchronized(this) {
            val builder = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addNetworkInterceptor { chain: Interceptor.Chain ->
                    val originalRequest = chain.request()
                    val headers = originalRequest.headers
                    val headersBuilder: Headers.Builder = RequestHeader.get()
                    for (name in headers.names()) {
                        if (headersBuilder[name] == null) {
                            headers[name]?.let { headersBuilder.add(name, it) }
                        } else {
                            headersBuilder[name] = headers[name].toString()
                        }
                    }
                    val authorizedRequest = originalRequest.newBuilder()
                        .headers(headersBuilder.build())
                        .build()
                    chain.proceed(authorizedRequest)
                }
            builder.addNetworkInterceptor(StethoInterceptor()).build()
            val temp = builder.build()
            instance = temp
            temp
        }
    }
}