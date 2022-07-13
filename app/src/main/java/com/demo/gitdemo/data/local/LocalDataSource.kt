package com.demo.gitdemo.data.local

import android.content.Context

class LocalDataSource private constructor(context: Context) : ILocalDataSource {

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource {
            return INSTANCE ?: synchronized(this) {
                val instance = LocalDataSource(context)
                INSTANCE = instance
                instance
            }
        }
    }
}