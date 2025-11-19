package com.example.imparkapk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.imparkapk.theme.ImparkApkTheme
import com.example.imparkapk.ui.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImparkApkTheme {
                val nav = rememberNavController()
                Scaffold() { paddingValues ->
                    AppNavGraph(nav, Modifier.padding(paddingValues))
                }
            }
        }
    }
}