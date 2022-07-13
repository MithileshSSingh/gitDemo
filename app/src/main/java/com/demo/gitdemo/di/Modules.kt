package com.demo.gitdemo.di

import com.demo.gitdemo.data.Repository
import com.demo.gitdemo.data.local.LocalDataSource
import com.demo.gitdemo.data.remote.RemoteDataSource
import org.koin.dsl.module

internal val applicationModule = module {
    single { Repository(get(), get()) }
    single { LocalDataSource.getInstance(get()) }
    single { RemoteDataSource.getInstance(get()) }
}