package com.example.imparkapk.di

import androidx.room.TypeConverter
object DateConverter {

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: java.util.Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun timestampToDate(timestamp: Long?): java.util.Date? = timestamp?.let { java.util.Date(it) }

    @TypeConverter
    @JvmStatic
    fun timeToTimestamp(time: java.sql.Time?): Long? = time?.time

    @TypeConverter
    @JvmStatic
    fun timestampToTime(timestamp: Long?): java.sql.Time? = timestamp?.let { java.sql.Time(it) }

}