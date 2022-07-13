package com.demo.gitdemo.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {
    fun hideKeyboard(activity: Activity) {
        if (!activity.isFinishing || !activity.isDestroyed) {
            try {
                val imm =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                var view = activity.currentFocus
                if (view == null) {
                    view = View(activity)
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {
                Log.v(activity.javaClass.canonicalName, e.message ?: "Error")
            }
        }
    }

}