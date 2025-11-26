package com.example.imparkapk.ui.feature.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.R
import com.example.imparkapk.ui.components.CardEstacionamento

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit = {},
    vm: DashboardViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    val inParkGreen = Color(0xFF1DB954)
    val inParkWhite = Color.White

    vm.loadEstacio()

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    contentDescription = "Logo InPark",
                    painter = painterResource(R.drawable.logo),
                    modifier = Modifier
                        .background(Color.White),
                )
            }

            Button(
                onClick = onLogout,
                content = { Text("LOGOUT") },
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            )
        }
        LazyColumn {
            for (estacionamento in state.estacionamentos) {
                item {
                    CardEstacionamento(
                        content = estacionamento
                    )
                }

            }
        }

    }
}