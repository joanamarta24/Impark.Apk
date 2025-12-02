import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imparkapk.data.dao.local.dao.dao.ClienteDao
import com.example.imparkapk.data.dao.local.dao.dao.EstacionamentoDao
import com.example.imparkapk.data.dao.local.dao.db.Converters
import com.example.imparkapk.data.dao.local.dao.entity.AcessoEntity
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity
import com.example.imparkapk.data.dao.local.dao.entity.EstacionamentoEntity
import com.example.imparkapk.data.dao.local.dao.entity.VeiculoEntity
import com.example.imparkapk.data.dao.remote.api.repository.acesso.AcessoDao

@Database(
    entities = [
        AcessoEntity::class,
        ClienteEntity::class,
        EstacionamentoEntity::class,
        VeiculoEntity::class,
        // ... outras entidades
    ],
    version = 2, // Incrementar versão
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun acessoDao(): AcessoDao
    abstract fun usuarioDao(): ClienteDao
    abstract fun estacionamentoDao(): EstacionamentoDao
    abstract fun veiculoDao(): VeiculoDao
    // ... outros DAOs

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "impark_database"
                )
                    .fallbackToDestructiveMigration() // Ou criar migrations manuais
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}