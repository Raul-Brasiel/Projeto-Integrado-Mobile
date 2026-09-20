package com.example.app_02_treinos_performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_02_treinos_performance.core.designSystem.App02treinosperformanceTheme
import com.example.app_02_treinos_performance.core.navigation.AppNavigation
import com.example.app_02_treinos_performance.data.AppDatabase
import kotlin.getValue
import com.example.app_02_treinos_performance.data.repository.FichaRepository
import com.example.app_02_treinos_performance.data.repository.ItemFichaRepository
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository
import com.example.app_02_treinos_performance.data.repository.SerieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val db by lazy { AppDatabase.getInstance(applicationContext, applicationScope) }
    private val fichaRepository by lazy { FichaRepository(db) }
    private val itemFichaRepository by lazy { ItemFichaRepository(db.itemFichaDao()) }
    private val exercicioRepository by lazy { ExercicioRepository(db.exercicioDao()) }
    private val serieRepository by lazy { SerieRepository(db.serieDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App02treinosperformanceTheme {
                AppNavigation(
                    fichaRepository = fichaRepository,
                    itemFichaRepository = itemFichaRepository,
                    exercicioRepository = exercicioRepository,
                    serieRepository = serieRepository
                )
            }
        }
    }
}