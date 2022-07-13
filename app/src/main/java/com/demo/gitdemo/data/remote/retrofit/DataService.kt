package com.demo.gitdemo.data.remote.retrofit

import com.demo.gitdemo.data.remote.model.PullRequestData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataService {
    @GET("/repos/{owner_repo}/pulls")
    fun getPullRequests(
        @Path("owner_repo", encoded = true) ownerRepo: String,
        @Query("state") state: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): Call<ArrayList<PullRequestData>>
}

