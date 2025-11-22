package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.Entity
import jakarta.persistence.Column
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table("dono")
data class DonoEntity(
    @Id
    @GereneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, length = 100)
    val nome: String,


)