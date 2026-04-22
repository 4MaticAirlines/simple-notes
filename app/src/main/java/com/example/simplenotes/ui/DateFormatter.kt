package com.example.simplenotes.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    private val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun format(timestamp: Long): String = formatter.format(Date(timestamp))
}
