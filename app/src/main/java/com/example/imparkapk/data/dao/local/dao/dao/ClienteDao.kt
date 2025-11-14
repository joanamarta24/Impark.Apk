package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
@Query("SELECT * FROM usuarios WHERE id =:id")
suspend fun getUsuarioById(id: String): ClienteEntity?

@Query("SELECT * FROM usuarios WHERE email =:email")
suspend fun inUsuarioByEmail(email: String): ClienteEntity?

@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
suspend fun insertUsuario(usuario: ClienteEntity)

@Update
suspend fun uodateUsuario(usuario: ClienteEntity)

@Query("DELETE FROM usuarios WHERE id =:id")
suspend fun deleteUsuarioById(id: String)

    @Query("SELECT * FROM usuarios WHERE ativo = 1")
    fun getUsuariosAtivos(): Flow<List<ClienteEntity>>

    @Query("UPDATE usuarios SET ativo = :ativo WHERE id = :id")
    suspend fun updateStatusUsuario(id: String, ativo: Boolean)

}