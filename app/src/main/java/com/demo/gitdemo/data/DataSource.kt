package com.demo.gitdemo.data

interface DataSource {
    interface CommonCallBack<T> {
        fun success(data: T?)
        fun failed(
            errorCode: Int,
            errorMessage: String,
            t: Throwable?
        )
    }
}