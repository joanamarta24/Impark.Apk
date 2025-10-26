package com.example.imparkapk.data.dao.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.qos.logback.classic.pattern.DateConverter
import com.example.imparkapk.data.dao.dao.AvaliacaoDao
import com.example.imparkapk.data.dao.dao.CarroDao
import com.example.imparkapk.data.dao.dao.EstacionamentoDao
import com.example.imparkapk.data.dao.dao.GerenteDao
import com.example.imparkapk.data.dao.dao.ReservaDao
import com.example.imparkapk.data.dao.dao.UsuarioDao
import com.example.imparkapk.data.dao.entity.CarroEntity
import com.example.imparkapk.data.dao.entity.UsuarioEntity


@Database(
    entities = [
        UsuarioEntity::class,
        CarroEntity::class,
        EstacionamentoEntity::class,
        GerenteEntity::class,
        ReservaEntity::class,
        AvaliacaoEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun carroDao(): CarroDao
    abstract fun estacionamentoDao(): EstacionamentoDao
    abstract fun gerenteDao(): GerenteDao
    abstract fun reservaDao(): ReservaDao
    abstract fun avaliacaoDao(): AvaliacaoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "impark_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}