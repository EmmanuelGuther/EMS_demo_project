package com.emmanuelguther.presentation.utils

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/**
 * Remove from timeStamp String minutes and seconds and return EpochMilli.
 */
fun String.timeStampToDateTruncatedToHours() = OffsetDateTime.parse(this).toInstant().truncatedTo(ChronoUnit.HOURS).toEpochMilli()

fun Long.getDateFromTimestamp() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
