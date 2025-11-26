package com.example.imparkapk.data.dao.local.dao.db

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    fun fromString(value: String?): List<String>? =
        value?.takeIf { it.isNotBlank() }?.split("|")

    @TypeConverter
    fun listToString(list: List<String>?): String? =
        list?.joinToString("|")

    }


