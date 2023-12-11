package com.app.replace.ui.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

data class DateUiModel(
    private val createdAt: String,
) {

    fun getDate(): String {
        val inputFormatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
            .toFormatter()
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        val dateTime = LocalDateTime.parse(createdAt, inputFormatter)

        return dateTime.format(outputFormatter)
    }
}
