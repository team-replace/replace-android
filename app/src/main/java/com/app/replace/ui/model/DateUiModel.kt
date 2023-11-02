package com.app.replace.ui.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DateUiModel(
    private val createdAt: String,
) {

    fun getDate(): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        val dateTime = LocalDateTime.parse(createdAt, inputFormatter)

        return dateTime.format(outputFormatter)
    }
}
