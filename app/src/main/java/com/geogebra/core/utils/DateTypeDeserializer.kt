package com.geogebra.core.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateTypeDeserializer : JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        return try {
            val value = json?.asString ?: ""
            if (value.isNullOrEmpty()){
                Date()
            } else {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value) ?: Date()
            }
        } catch (e: Exception) {
            Date()
        }
    }
}