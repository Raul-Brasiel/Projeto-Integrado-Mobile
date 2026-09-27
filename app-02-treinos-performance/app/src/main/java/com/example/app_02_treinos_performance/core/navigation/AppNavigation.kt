package com.example.app_02_treinos_performance.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.app_02_treinos_performance.core.designSystem.components.BarraNavegacaoInferior
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho
import com.example.app_02_treinos_performance.data.repository.CardioRepository
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository
import com.example.app_02_treinos_performance.data.repository.ItemFichaRepository
import com.example.app_02_treinos_performance.data.repository.SerieRepository
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaScreen
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaViewModel
import com.example.app_02_treinos_performance.feature.detalhesFicha.DetalhesFichaViewModelFactory
import com.example.app_02_treinos_performance.feature.listaCardio.ListaCardioViewModel
import com.example.app_02_treinos_performance.feature.listaCardio.ListaCardioViewModelFactory
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaScreen
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaViewModel
import com.example.app_02_treinos_performance.feature.novaFicha.NovaFichaViewModelFactory
import com.example.app_02_treinos_performance.feature.registrarCarga.RegistrarCargaScreen
import com.example.app_02_treinos_performance.feature.registrarCarga.RegistrarCargaViewModel
import com.example.app_02_treinos_performance.feature.registrarCarga.RegistrarCargaViewModelFactory
import com.example.app_02_treinos_performance.feature.cadastroCardio.CadastroCardioScreen
import com.example.app_02_treinos_performance.feature.cadastroCardio.CadastroCardioViewModel
import com.example.app_02_treinos_performance.feature.cadastroCardio.CadastroCardioViewModelFactory

private const val ROTA_NOVA_FICHA = "novaFicha"
private const val ROTA_DETALHES_FICHA = "detalhesFicha"
private const val ROTA_REGISTRAR_CARGA = "registrarCarga"
private const val ROTA_ADICIONAR_EXERCICIO = "adicionarExercicio"
private const val ROTA_CADASTRO_CARDIO = "cadastroCardio"

@Composable
fun AppNavigation(
    fichaRepository: FichaRepository,
    itemFichaRepository: ItemFichaRepository,
    exercicioRepository: ExercicioRepository,
    serieRepository: SerieRepository,
    cardioRepository: CardioRepository
) {
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
                val homeViewModel: HomeViewModel = viewModel(
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
                val listaCardioViewModel: ListaCardioViewModel =
                    viewModel(
                        factory = ListaCardioViewModelFactory(cardioRepository)
                    )

                ListaCardioScreen(
                    viewModel = listaCardioViewModel,
                    onAdicionarCardio = {
                        navController.navigate(ROTA_CADASTRO_CARDIO)
                    },
                    onEditarCardio = { cardioId ->
                        navController.navigate("$ROTA_CADASTRO_CARDIO?cardioId=$cardioId")
                    }
                )
            }

            composable(
                route = "$ROTA_CADASTRO_CARDIO?cardioId={cardioId}",
                arguments = listOf(
                    navArgument("cardioId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val cardioIdArg = backStackEntry.arguments?.getLong("cardioId") ?: -1L
                val cardioIdParaEditar = cardioIdArg.takeIf { it != -1L }

                val cadastroCardioViewModel: CadastroCardioViewModel = viewModel(
                    factory = CadastroCardioViewModelFactory(cardioRepository, cardioIdParaEditar)
                )

                CadastroCardioScreen(
                    viewModel = cadastroCardioViewModel,
                    onVoltar = { navController.popBackStack() },
                    onSalvo = { navController.popBackStack() }
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

                val novaFichaViewModel: NovaFichaViewModel = viewModel(
                    factory = NovaFichaViewModelFactory(fichaRepository, fichaIdParaEditar)
                )

                val itensAdicionados by backStackEntry.savedStateHandle
                    .getStateFlow<ArrayList<ItemFichaRascunho>?>("itensAdicionados", null)
                    .collectAsStateWithLifecycle()

                LaunchedEffect(itensAdicionados) {
                    itensAdicionados?.let { lista ->
                        lista.forEach { novaFichaViewModel.adicionarItem(it) }
                        backStackEntry.savedStateHandle["itensAdicionados"] = null
                    }
                }

                NovaFichaScreen(
                    viewModel = novaFichaViewModel,
                    onVoltar = { navController.popBackStack() },
                    onFichaSalva = { navController.popBackStack() },
                    onAdicionarExercicio = { navController.navigate(ROTA_ADICIONAR_EXERCICIO) },
                    onEditarExercicio = { _, item ->
                        item.itemFichaId?.let { itemFichaId ->
                            navController.navigate("$ROTA_REGISTRAR_CARGA/$itemFichaId")
                        }
                    }
                )
            }

            composable(
                route = "$ROTA_DETALHES_FICHA/{fichaId}",
                arguments = listOf(navArgument("fichaId") { type = NavType.LongType })
            ) { backStackEntry ->
                val fichaId = backStackEntry.arguments?.getLong("fichaId") ?: return@composable

                val detalhesFichaViewModel: DetalhesFichaViewModel = viewModel(
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
                route = "$ROTA_REGISTRAR_CARGA/{itemFichaId}",
                arguments = listOf(navArgument("itemFichaId") { type = NavType.LongType })
            ) { backStackEntry ->
                val itemFichaId = backStackEntry.arguments?.getLong("itemFichaId") ?: return@composable

                val registrarCargaViewModel: RegistrarCargaViewModel = viewModel(
                        factory = RegistrarCargaViewModelFactory(
                            serieRepository, itemFichaRepository, exercicioRepository, itemFichaId
                        )
                    )
                RegistrarCargaScreen(
                    viewModel = registrarCargaViewModel,
                    onVoltar = { navController.popBackStack() },
                    onSalvo = { navController.popBackStack() }
                )
            }

            composable(ROTA_ADICIONAR_EXERCICIO) {
                val viewModel: com.example.app_02_treinos_performance.feature.adicionarExercicios.AdicionarExercicioViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel(
                        factory = com.example.app_02_treinos_performance.feature.adicionarExercicios.AdicionarExercicioViewModelFactory(exercicioRepository)
                    )

                com.example.app_02_treinos_performance.feature.adicionarExercicios.AdicionarExercicioScreen(
                    viewModel = viewModel,
                    onVoltar = { itensAdicionados ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "itensAdicionados",
                            ArrayList(itensAdicionados)
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}