package com.mavenclinic

import com.soywiz.klock.Time
import com.soywiz.klock.TimeFormat
import com.soywiz.klock.format
import com.soywiz.klock.parseTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
/**
 * A wrapper around a Klock Time that allows it to be serialized/deserialized.
 *
 * Handles the time formatting as returned by the Sunrise/Sunset API. (Could
 * obviously be generalized).
 *
 * Also saves the problem that Klock Time is implemented as a value type, which
 * aren't currently supported by Kotlin Serialization.
 */

//thoughts on maintainability: if this weren't a "free" class, I'd implement the serializer separately
//my general opinions tend to liking models to "just" be models. It also helps keep code clean on more
//complex data types
data class ApiTime(
    val value: Time
) {
    @Serializer( forClass = ApiTime::class )
    companion object : KSerializer<ApiTime> {
        private val timeFormat = TimeFormat("h:mm:ss a")
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("Time", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): ApiTime {
            return ApiTime( timeFormat.parseTime(decoder.decodeString().toLowerCase()))
        }
        override fun serialize(encoder: Encoder, value: ApiTime) {
            encoder.encodeString( timeFormat.format( value.value ) )
        }
        fun fromTime( time: Time) = ApiTime(time)
    }
    fun toTime(): Time = value
}
