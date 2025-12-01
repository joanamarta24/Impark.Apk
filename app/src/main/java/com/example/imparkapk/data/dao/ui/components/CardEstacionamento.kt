package com.example.imparkapk.data.dao.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.imparkapk.data.dao.model.Estacionamento

@Composable
fun CardEstacionamento (
    content: Estacionamento
){
    Card {
        Text(content.nome)
        Text(content.endereco)
    }
}