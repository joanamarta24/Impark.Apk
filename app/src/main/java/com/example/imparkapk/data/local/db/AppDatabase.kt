package com.example.imparkapk.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imparkapk.data.local.dao.ReservaDao
import com.example.imparkapk.data.local.dao.AvaliacaoDao
import com.example.imparkapk.data.local.dao.CarroDao
import com.example.imparkapk.data.local.dao.EstacionamentoDao
import com.example.imparkapk.data.local.dao.usuarios.GerenteDao
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import com.example.imparkapk.data.local.entity.ReservaEntity


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