package com.example.imparkapk.data.dao.local.dao.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imparkapk.data.dao.local.dao.dao.AvaliacaoDao
import com.example.imparkapk.data.dao.local.dao.dao.CarroDao
import com.example.imparkapk.data.dao.local.dao.dao.ClienteDao
import com.example.imparkapk.data.dao.local.dao.dao.EstacionamentoDao
import com.example.imparkapk.data.dao.local.dao.dao.ReservaDao
import com.example.imparkapk.data.dao.local.dao.entity.AvaliacaoEntity
import com.example.imparkapk.data.dao.local.dao.entity.CarroEntity
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity
import com.example.imparkapk.data.dao.local.dao.entity.EstacionamentoEntity
import com.example.imparkapk.data.dao.local.dao.entity.ReservaEntity

@Database(
    entities = [
        ClienteEntity::class,
        CarroEntity::class,
        EstacionamentoEntity::class,
        ReservaEntity::class,
        AvaliacaoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun carroDao(): CarroDao
    abstract fun estacionamentoDao(): EstacionamentoDao
    abstract fun reservaDao(): ReservaDao
    abstract fun avaliacaoDao(): AvaliacaoDao

}