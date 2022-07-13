package com.demo.gitdemo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.gitdemo.data.DataSource
import com.demo.gitdemo.data.Repository
import com.demo.gitdemo.data.remote.model.PullRequestData
import org.koin.java.KoinJavaComponent.inject

class PullRequestViewModel : ViewModel() {
    private var state = "closed"
    private var pageSize = 20
    private val repository: Repository by inject(Repository::class.java)

    private val _loadingInitPageObserver = MutableLiveData<Boolean>()
    var loadingInitPageObserver: LiveData<Boolean> = _loadingInitPageObserver

    private val _loadingNewPageObserver = MutableLiveData<Boolean>()
    var loadingNewPageObserver: LiveData<Boolean> = _loadingNewPageObserver

    private val _pullRequestDataObserver = MutableLiveData<ArrayList<PullRequestData>?>()
    var pullRequestDataObserver: LiveData<ArrayList<PullRequestData>?> = _pullRequestDataObserver

    private val _errorObserver = MutableLiveData<String?>()
    var errorObserver: LiveData<String?> = _errorObserver

    var ownerAndRepo = "torvalds/linux"

    fun getPullRequest(pageNumber: Int) {
        showLoader(pageNumber)

        repository.getPullRequest(
            state,
            pageNumber,
            pageSize,
            ownerAndRepo,
            object : DataSource.CommonCallBack<ArrayList<PullRequestData>> {
                override fun success(data: ArrayList<PullRequestData>?) {
                    hideLoader(pageNumber)
                    _pullRequestDataObserver.value = data
                    handleError(null,null)
                }

                override fun failed(errorCode: Int, errorMessage: String, t: Throwable?) {
                    hideLoader(pageNumber)
                    handleError(errorMessage,t)
                }
            })
    }

    private fun handleError(errorMessage: String?, t: Throwable?) {
        if(errorMessage.isNullOrEmpty()){
            _errorObserver.value = t?.message
        }else{
            _errorObserver.value = errorMessage
        }
    }

    private fun showLoader(pageNumber: Int) {
        if (pageNumber == 0) {
            _loadingInitPageObserver.value = true
        } else {
            _loadingNewPageObserver.value = true
        }
    }

    private fun hideLoader(pageNumber: Int) {
        if (pageNumber == 0) {
            _loadingInitPageObserver.value = false
        } else {
            _loadingNewPageObserver.value = false
        }
    }
}