package com.demo.gitdemo.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.gitdemo.R
import com.demo.gitdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        showPullRequestListFragment()
    }

    private fun showPullRequestListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                PullRequestListFragment.getInstance(),
                PullRequestListFragment.TAG
            )
            .commit()
    }
}