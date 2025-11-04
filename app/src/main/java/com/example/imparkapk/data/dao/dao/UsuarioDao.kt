package com.example.imparkapk.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
@Query("SELECT * FROM usuarios WHERE id =:id")
suspend fun getUsuarioById(id: String): UsuarioEntity?

@Query("SELECT * FROM usuarios WHERE email =:email")
suspend fun inUsuarioByEmail(email: String): UsuarioEntity?

@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
suspend fun insertUsuario(usuario: UsuarioEntity)

@Update
suspend fun uodateUsuario(usuario: UsuarioEntity)

@Query("DELETE FROM usuarios WHERE id =:id")
suspend fun deleteUsuarioById(id: String)

    @Query("SELECT * FROM usuarios WHERE ativo = 1")
    fun getUsuariosAtivos(): Flow<List<UsuarioEntity>>

    @Query("UPDATE usuarios SET ativo = :ativo WHERE id = :id")
    suspend fun updateStatusUsuario(id: String, ativo: Boolean)

}