package com.finance.presentation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val FORMAT_FULL_MONTH_NAME = "MMMM"

fun getMonthNameFromLongDate(dateInMillis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateInMillis
    val monthNumber = calendar.get(Calendar.MONTH)
    val dateFormat = SimpleDateFormat(FORMAT_FULL_MONTH_NAME, Locale.getDefault())
    calendar.set(Calendar.MONTH, monthNumber)
    return dateFormat.format(calendar.time)
}

fun areDatesInSameMonth(date1: Long, date2: Long): Boolean {
    val calendar1 = Calendar.getInstance().apply {
        timeInMillis = date1
    }
    val calendar2 = Calendar.getInstance().apply {
        timeInMillis = date2
    }

    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
}

fun getStartTimeFromParticularMonth(date: Long): Long {
    return Calendar.getInstance().apply {
        time = Date(date)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 1)
    }.time.time
}

fun getEndTimeFromParticularMonth(date: Long): Long {
    return Calendar.getInstance().apply {
        time = Date(date)
        set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
    }.time.time
}