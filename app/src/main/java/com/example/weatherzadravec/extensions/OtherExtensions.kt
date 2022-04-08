package com.example.weatherzadravec.extensions

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss"): Date {
    val formatter = SimpleDateFormat(dateFormat)
    return formatter.parse(this)
}

fun String.toDayOfWeek(): String {
    val cal = Calendar.getInstance()
    cal.time = this.toDate()
    val dayOfWeek = cal[Calendar.DAY_OF_WEEK]

    return DateFormatSymbols().shortWeekdays[dayOfWeek]
}


