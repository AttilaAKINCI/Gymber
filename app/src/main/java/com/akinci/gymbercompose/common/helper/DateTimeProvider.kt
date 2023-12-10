package com.akinci.gymbercompose.common.helper

import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateTimeProvider {

    fun findOpeningTime(time: String): String{
        //2019-03-25T14:10:12+01:00
        var response = ""
        try {
            val localDate = OffsetDateTime.parse(time).toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            response = localDate.format(formatter)
        }catch (e: Exception){ Timber.d("Date parse Exception ${e.message}") }
        return response
    }
}