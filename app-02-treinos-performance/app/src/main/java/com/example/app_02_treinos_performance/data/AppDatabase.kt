package com.example.app_02_treinos_performance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_02_treinos_performance.data.local.ExercicioDAO
import com.example.app_02_treinos_performance.data.local.FichaDAO
import com.example.app_02_treinos_performance.data.local.ItemFichaDAO
import com.example.app_02_treinos_performance.data.model.Exercicio
import com.example.app_02_treinos_performance.data.model.Ficha
import com.example.app_02_treinos_performance.data.model.ItemFicha

@Database(
    entities = [Ficha::class, Exercicio::class, ItemFicha::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fichaDao(): FichaDAO
    abstract fun exercicioDao(): ExercicioDAO
    abstract fun itemFichaDao(): ItemFichaDAO
}