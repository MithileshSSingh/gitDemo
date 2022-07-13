package com.demo.gitdemo.data

import com.demo.gitdemo.data.local.ILocalDataSource
import com.demo.gitdemo.data.local.LocalDataSource
import com.demo.gitdemo.data.remote.IRemoteDataSource
import com.demo.gitdemo.data.remote.RemoteDataSource
import com.demo.gitdemo.data.remote.model.PullRequestData

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IRemoteDataSource, ILocalDataSource {

    override fun getPullRequest(
        state: String,
        pageNumber: Int,
        pageSize: Int,
        ownerAndRepo:String,
        callBack: DataSource.CommonCallBack<ArrayList<PullRequestData>>
    ) {
        remoteDataSource.getPullRequest(
            state,
            pageNumber,
            pageSize,
            ownerAndRepo,
            object : DataSource.CommonCallBack<ArrayList<PullRequestData>> {
                override fun success(data: ArrayList<PullRequestData>?) {
                    callBack.success(data)
                }

                override fun failed(errorCode: Int, errorMessage: String, t: Throwable?) {
                    callBack.failed(errorCode, errorMessage, t)
                }
            })
    }
}