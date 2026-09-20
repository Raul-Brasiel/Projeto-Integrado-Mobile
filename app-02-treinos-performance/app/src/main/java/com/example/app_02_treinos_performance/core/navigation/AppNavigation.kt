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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.app_02_treinos_performance.core.designSystem.components.BarraNavegacaoInferior
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaScreen
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaViewModel
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaViewModelFactory
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaScreen
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaViewModel
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaViewModelFactory

private const val ROTA_NOVA_FICHA = "novaFicha"
private const val ROTA_DETALHES_FICHA = "detalhesFicha"

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

            if (rotaSelecionada != null) {
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
                    onAdicionarFicha = { navController.navigate(ROTA_NOVA_FICHA) },
                    onFichaClick = { fichaId ->
                        navController.navigate("$ROTA_DETALHES_FICHA/$fichaId")
                    }
                )
            }

            composable(BarraInferiorNavigation.CARDIO.rota) {
                ListaCardioScreen()
            }

            composable(
                route = "$ROTA_DETALHES_FICHA/{fichaId}",
                arguments = listOf(navArgument("fichaId") { type = NavType.LongType })
            ) { backStackEntry ->
                val fichaId = backStackEntry.arguments?.getLong("fichaId") ?: return@composable

                val detalhesFichaViewModel: DetalhesFichaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = DetalhesFichaViewModelFactory(fichaRepository, fichaId)
                )
                DetalhesFichaScreen(
                    viewModel = detalhesFichaViewModel,
                    onVoltar = { navController.popBackStack() },
                    onEditarFicha = { id ->
                        navController.navigate("$ROTA_NOVA_FICHA?fichaId=$id")
                    }
                )
            }

            composable(
                route = "$ROTA_NOVA_FICHA?fichaId={fichaId}",
                arguments = listOf(
                    navArgument("fichaId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val fichaIdArg = backStackEntry.arguments?.getLong("fichaId") ?: -1L
                val fichaIdParaEditar = fichaIdArg.takeIf { it != -1L }

                val novaFichaViewModel: NovaFichaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = NovaFichaViewModelFactory(fichaRepository, fichaIdParaEditar)
                )
                NovaFichaScreen(
                    viewModel = novaFichaViewModel,
                    onVoltar = { navController.popBackStack() },
                    onFichaSalva = { navController.popBackStack() },
                    onAdicionarExercicio = {
                    },
                    onEditarExercicio = { _, _ ->
                    }
                )
            }
        }
    }
}