package com.example.imparkapk.data.repository.usuarios

import android.content.Context
import com.example.imparkapk.data.local.dao.usuarios.ClienteDao
import com.example.imparkapk.data.mapper.usuarios.toDomain
import com.example.imparkapk.data.remote.api.api.usuarios.ClienteApi
import com.example.imparkapk.di.IoDispatcher
import com.example.imparkapk.domain.model.usuarios.Cliente
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClienteRepository @Inject constructor(
    val dao: ClienteDao,
    val api: ClienteApi,
    @IoDispatcher private val io: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val gson: Gson
){
    private val jsonMedia = "application/json".toMediaType()

    private fun partJasonData(dados: Any): RequestBody =
        gson.toJson(dados).toRequestBody(jsonMedia)

    fun observeAll(): Flow<List<Cliente>> = dao.observerAll().map { list -> list.map { it.toDomain() } }

    fun observeById(id: Long): Flow<Cliente?> = dao.observeById(id).map { it?.toDomain() }
}