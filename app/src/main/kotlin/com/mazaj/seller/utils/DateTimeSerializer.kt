package com.mazaj.seller.utils

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

private const val DAY_FORMAT = "yyyy-MM-dd"

open class DateTimeSerializer(private var formatter: DateTimeFormatter = ISODateTimeFormat.dateTimeParser().withOffsetParsed()) : KSerializer<DateTime> {
    override val descriptor = PrimitiveSerialDescriptor("DateTimeAsStringSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTime) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): DateTime = DateTime.parse(decoder.decodeString(), formatter)
}

class DaySerializer : DateTimeSerializer(DateTimeFormat.forPattern(DAY_FORMAT))
