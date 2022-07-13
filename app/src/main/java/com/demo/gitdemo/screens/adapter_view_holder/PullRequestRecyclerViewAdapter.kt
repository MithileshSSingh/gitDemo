package com.demo.gitdemo.screens.adapter_view_holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.gitdemo.R
import com.demo.gitdemo.data.remote.model.PullRequestData
import com.demo.gitdemo.databinding.ItemPullRequestBinding
import com.demo.gitdemo.utils.DateTimeUtils

class PullRequestRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: ArrayList<PullRequestData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PullRequestViewHolder(
            ItemPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PullRequestViewHolder -> holder.apply(dataList[position], position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(data: ArrayList<PullRequestData>) {
        clearData()
        dataList.addAll(data)
        notifyItemRangeInserted(dataList.size - data.size, data.size)
    }

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun updateData(data: ArrayList<PullRequestData>) {
        dataList.addAll(data)
        notifyItemRangeInserted(dataList.size - data.size, data.size)
    }

    inner class PullRequestViewHolder(
        private val binding: ItemPullRequestBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun apply(data: PullRequestData, position: Int) {
            binding.apply {
                tvTitle.text = data.title
                tvCreatedAtTime.text = DateTimeUtils.serverTimeTo_dd_MMM_YYY(data.createdAt)
                tvClosedAtTime.text = DateTimeUtils.serverTimeTo_dd_MMM_YYY(data.closedAt)
                tvUserName.text = itemView.context.getString(R.string.user_name, data.user?.userName)

                Glide.with(context)
                    .load(data.user?.avatarUrl)
                    .centerCrop()
                    .circleCrop()
                    .into(ivProfilePic)
            }
        }
    }
}