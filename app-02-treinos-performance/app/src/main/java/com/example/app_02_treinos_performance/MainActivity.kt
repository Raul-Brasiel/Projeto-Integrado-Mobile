package com.example.app_02_treinos_performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_02_treinos_performance.core.designSystem.App02treinosperformanceTheme
import androidx.room.Room
import com.example.app_02_treinos_performance.core.navigation.AppNavigation
import com.example.app_02_treinos_performance.data.AppDatabase
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "treinos_db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App02treinosperformanceTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App02treinosperformanceTheme {
    }
}