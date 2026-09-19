package com.example.app_02_treinos_performance.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_02_treinos_performance.data.repository.FichaRepository
import com.example.app_02_treinos_performance.feature.home.HomeScreen
import com.example.app_02_treinos_performance.feature.home.HomeViewModel
import com.example.app_02_treinos_performance.feature.home.HomeViewModelFactory
import com.example.app_02_treinos_performance.feature.listaCardio.ListaCardioScreen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.app_02_treinos_performance.core.designSystem.components.BarraNavegacaoInferior

@Composable
fun AppNavigation(fichaRepository: FichaRepository) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val rotaSelecionada = BarraInferiorNavigation.entries
                .firstOrNull { item -> currentDestination?.hierarchy?.any { it.route == item.rota } == true }
                ?.rota

            BarraNavegacaoInferior(
                rotaSelecionada = rotaSelecionada,
                onItemClick = { item ->
                    navController.navigate(item.rota) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BarraInferiorNavigation.FICHAS.rota,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BarraInferiorNavigation.FICHAS.rota) {
                val homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = HomeViewModelFactory(fichaRepository)
                )
                HomeScreen(
                    viewModel = homeViewModel,
                    onAdicionarFicha = {
                    },
                    onFichaClick = { fichaId ->
                    }
                )
            }
            composable(BarraInferiorNavigation.CARDIO.rota) {
                ListaCardioScreen()
            }
        }
    }
}