package com.example.app_02_treinos_performance.core.designSystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.core.navigation.BarraInferiorNavigation

@Composable
fun BarraNavegacaoInferior(
    rotaSelecionada: String?,
    onItemClick: (BarraInferiorNavigation) -> Unit
) {
    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            BarraInferiorNavigation.entries.forEach { item ->
                NavigationBarItem(
                    selected = rotaSelecionada == item.rota,
                    onClick = { onItemClick(item) },
                    icon = { Icon(item.icone, contentDescription = item.label) },
                    label = { Text(item.label) }
                )
            }
        }
    }
}