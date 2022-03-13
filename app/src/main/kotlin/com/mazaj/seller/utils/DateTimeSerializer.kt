package com.mazaj.seller.utils

import kotlinx.serialization.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

private const val DAY_FORMAT = "yyyy-MM-dd"

open class DateTimeSerializer(private var formatter: DateTimeFormatter = ISODateTimeFormat.dateTimeParser().withOffsetParsed()) : KSerializer<DateTime> {
    override val descriptor = PrimitiveDescriptor("DateTimeAsStringSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTime) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): DateTime = DateTime.parse(decoder.decodeString(), formatter)
}

class DaySerializer : DateTimeSerializer(DateTimeFormat.forPattern(DAY_FORMAT))
