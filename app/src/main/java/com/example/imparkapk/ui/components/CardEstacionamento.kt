package com.example.imparkapk.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.imparkapk.domain.model.Estacionamento

@Composable
fun CardEstacionamento(
    content: Estacionamento
) {
    Card {
        Text(content.nome)
        Text(content.endereco)
    }
}