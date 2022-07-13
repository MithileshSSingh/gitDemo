package com.demo.gitdemo.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.github.com"

    fun getRetrofitInstance(): Retrofit {
        return retrofit
            ?: synchronized(this) {
            val temp = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpProvider.get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit = temp
            temp
        }
    }
}