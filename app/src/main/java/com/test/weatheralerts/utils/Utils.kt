package com.test.weatheralerts.utils

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private const val DATE_FORMAT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss"
private const val SIMPLE_DATE_TEMPLATE = "MM/dd/yyyy"
fun String.formatAndRemoveTimeDetailsFromStringUTC(): String? {
    val inputFormat = SimpleDateFormat(DATE_FORMAT_TIMEZONE, Locale.US)
    val outputFormat = SimpleDateFormat(SIMPLE_DATE_TEMPLATE, Locale.US)
    outputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) }
}

fun getDifferenceBetweenTwoStringDates(startDate: String, endDate: String): String {
    val start = SimpleDateFormat(DATE_FORMAT_TIMEZONE, Locale.getDefault()).parse(startDate)
    val end = SimpleDateFormat(DATE_FORMAT_TIMEZONE, Locale.getDefault()).parse(endDate)
    val diff: Long? = start?.time?.let { end?.time?.minus(it) }
    val seconds = diff?.div(1000)
    val minutes = seconds?.div(60)
    val hours = minutes?.div(60)
    val days = hours?.div(24)
    val hoursLeft = hours?.rem(24)
    val minutesLeft = minutes?.rem(60)

    return "$days days $hoursLeft hours $minutesLeft minutes"
}


fun <E> applySchedulersWithObserverSingle(): SingleTransformer<E, E> {
    return SingleTransformer<E, E> { single: Single<E> ->
        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
    }
}