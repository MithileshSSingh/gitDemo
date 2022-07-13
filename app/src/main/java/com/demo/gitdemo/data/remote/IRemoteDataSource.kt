package com.demo.gitdemo.data.remote

import com.demo.gitdemo.data.DataSource
import com.demo.gitdemo.data.remote.model.PullRequestData

interface IRemoteDataSource : DataSource {
    fun getPullRequest(
        state: String,
        pageNumber: Int,
        pageSize: Int,
        ownerAndRepo:String,
        callBack: DataSource.CommonCallBack<ArrayList<PullRequestData>>
    )
}