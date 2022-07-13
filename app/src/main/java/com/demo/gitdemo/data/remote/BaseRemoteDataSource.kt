package com.demo.gitdemo.data.remote

import com.demo.gitdemo.data.DataSource
import com.demo.gitdemo.data.remote.model.ResponseError
import com.demo.gitdemo.data.remote.retrofit.DataService
import com.demo.gitdemo.data.remote.retrofit.RetrofitClientInstance
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

open class BaseRemoteDataSource {

    fun getDataService(): DataService? {
        return RetrofitClientInstance.getRetrofitInstance().create(DataService::class.java)
    }

    class ComputeResponse<T> {
        fun computeResponse(call: Call<T>?, callBack: DataSource.CommonCallBack<T>) {

            call?.enqueue(object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    data: Response<T>
                ) {
                    if (data.body() != null) {
                        callBack.success(data.body())
                    } else if (data.errorBody() != null) {
                        callBack.failed(
                            0,
                            getErrorMessageFromErrorBody(data.errorBody(), "Please try again"),
                            null
                        )
                    } else {
                        data.errorBody()
                        callBack.failed(0, "Please try again", null)
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val errorCode: Int = getErrorCode(t)
                    val errorMessage: String = getErrorMessage(t)

                    callBack.failed(errorCode, errorMessage, t)
                }
            })
        }


        fun getErrorCode(t: Throwable?): Int {
            return (t as HttpException).code()
        }

        fun getErrorMessage(t: Throwable): String {
            return t.message ?: ""
        }

        private fun getErrorMessageFromErrorBody(
            errorBody: ResponseBody?,
            defaultError: String
        ): String {
            var error: String? = null
            var errorMessage = defaultError
            try {
                error = errorBody?.string()
                val responseError: ResponseError? =
                    Gson().fromJson(error, ResponseError::class.java)
                if (!responseError?.message.isNullOrEmpty()) {
                    errorMessage = responseError?.message ?: defaultError
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.message ?: defaultError
            }
            return errorMessage
        }

    }

}