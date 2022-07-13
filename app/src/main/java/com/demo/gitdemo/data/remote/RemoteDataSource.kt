package com.demo.gitdemo.data.remote

import android.content.Context
import com.demo.gitdemo.data.DataSource
import com.demo.gitdemo.data.remote.model.PullRequestData

class RemoteDataSource private constructor(context: Context) : IRemoteDataSource,
    BaseRemoteDataSource() {

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(context: Context): RemoteDataSource {
            return INSTANCE ?: synchronized(this) {
                val instance = RemoteDataSource(context)
                INSTANCE = instance
                instance
            }
        }
    }

    override fun getPullRequest(
        state: String,
        pageNumber: Int,
        pageSize: Int,
        ownerAndRepo: String,
        callBack: DataSource.CommonCallBack<ArrayList<PullRequestData>>
    ) {
        val call = getDataService()?.getPullRequests(ownerAndRepo, state, pageNumber, pageSize)
        ComputeResponse<ArrayList<PullRequestData>>().computeResponse(
            call,
            callBack
        )
    }
}