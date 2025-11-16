package com.example.imparkapk.data.dao.local.dao.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imparkapk.data.dao.local.dao.dao.ClienteDao
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity

@Database(entities = [ClienteEntity::class], version = 5)
@TypeConverters(Converters::class)

abstract class AppDataBase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    
}
