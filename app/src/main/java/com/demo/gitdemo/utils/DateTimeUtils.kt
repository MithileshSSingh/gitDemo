package com.demo.gitdemo.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun serverTimeTo_dd_MMM_YYY(serverDate: String?): String? {
        var newFormat = serverDate
        try {
            val oldPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
            val newPattern = "dd MMM yyyy"
            var oldDate: Date? = null
            val sdf = SimpleDateFormat(oldPattern)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            oldDate = sdf.parse(serverDate)
            val formatter = SimpleDateFormat(newPattern)
            newFormat = formatter.format(oldDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return newFormat
    }

}