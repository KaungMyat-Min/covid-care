package me.justdeveloper.carecovid.network.converters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer(
    private val formatter: SimpleDateFormat
) : JsonDeserializer<Date?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        return json?.let {
            formatter.parse(it.asString)
        }
    }
}
