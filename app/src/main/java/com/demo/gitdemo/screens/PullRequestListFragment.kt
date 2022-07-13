package com.demo.gitdemo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.gitdemo.R
import com.demo.gitdemo.data.remote.model.PullRequestData
import com.demo.gitdemo.databinding.FragmentPrListBinding
import com.demo.gitdemo.screens.adapter_view_holder.PullRequestRecyclerViewAdapter
import com.demo.gitdemo.utils.AndroidUtils
import com.demo.gitdemo.utils.EndlessRecyclerViewScrollListener

class PullRequestListFragment : Fragment() {

    companion object {
        const val TAG: String = "PullRequestListFragment"
        fun getInstance() = PullRequestListFragment()
    }

    lateinit var binding: FragmentPrListBinding
    lateinit var pullRequestViewModel: PullRequestViewModel
    lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener
    lateinit var pullRequestRecyclerViewAdapter: PullRequestRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViewModel()
        initView()
        initData()
    }

    private fun initViewModel() {
        pullRequestViewModel = ViewModelProvider(this).get(PullRequestViewModel::class.java)

        pullRequestViewModel.loadingNewPageObserver.observe(
            viewLifecycleOwner,
            Observer { showLoading(it) })
        pullRequestViewModel.loadingInitPageObserver.observe(
            viewLifecycleOwner,
            Observer { showFullScreenLoader(it) }
        )
        pullRequestViewModel.pullRequestDataObserver.observe(
            viewLifecycleOwner,
            Observer { populateData(it) })
        pullRequestViewModel.errorObserver.observe(
            viewLifecycleOwner,
            Observer { showErrorLayout(it) }
        )
    }

    private fun initView() {
        binding.apply {
            btnGo.setOnClickListener {
                if (!etSearch.text?.trim().isNullOrEmpty()) {
                    pullRequestViewModel.ownerAndRepo = etSearch.text.toString()
                    initData()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.owner_repo_toast_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                AndroidUtils.hideKeyboard(requireActivity())
            }
        }
        initRecyclerView()
    }

    private fun initData() {
        pullRequestRecyclerViewAdapter.clearData()
        pullRequestViewModel.getPullRequest(0)
    }

    private fun initRecyclerView() {
        pullRequestRecyclerViewAdapter = PullRequestRecyclerViewAdapter()
        binding.rvPullRequest.apply {
            setHasFixedSize(true)
            adapter = pullRequestRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            endlessScrollListener = object :
                EndlessRecyclerViewScrollListener(this.layoutManager, PAGINATION_LOAD_THRESHOLD) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    pullRequestViewModel.getPullRequest(page)
                }
            }
            addOnScrollListener(endlessScrollListener)
        }
    }

    private fun populateData(data: ArrayList<PullRequestData>?) {
        binding.apply {
            tvRepoDetail.text =
                getString(R.string.showing_pr_for_repo, pullRequestViewModel.ownerAndRepo)
        }
        data?.let {
            pullRequestRecyclerViewAdapter.updateData(it)
        }
    }

    private fun showErrorLayout(errorMessage: String?) {
        binding.apply {
            if (errorMessage.isNullOrEmpty()) {
                rvPullRequest.visibility = View.VISIBLE
                errorLayout.root.visibility = View.GONE
            } else {
                rvPullRequest.visibility = View.GONE
                errorLayout.root.visibility = View.VISIBLE
                errorLayout.tvErrorMessage.text = errorMessage
            }
        }
    }

    private fun showFullScreenLoader(show: Boolean) {
        binding.apply {
            if (show) {
                rvPullRequest.visibility = View.GONE
                tvRepoDetail.visibility = View.GONE
                pbFullScreenLoader.visibility = View.VISIBLE
                errorLayout.root.visibility = View.GONE
            } else {
                rvPullRequest.visibility = View.VISIBLE
                tvRepoDetail.visibility = View.VISIBLE
                pbFullScreenLoader.visibility = View.GONE
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.apply {
            pbLoadingNewData.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}