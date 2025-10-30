package com.example.imparkapk.data.dao.repository

import com.example.imparktcc.data.local.dao.ReservaDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservaRepositoryImpl @Inject constructor(
    private val reservaDao: ReservaDao,
    private  val reservaApi:ReservaApi
){
}