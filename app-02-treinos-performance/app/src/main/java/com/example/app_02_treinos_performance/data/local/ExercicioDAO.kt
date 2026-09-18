package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.Exercicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercicioDAO {
    @Insert
    suspend fun inserir(exercicio: Exercicio): Long

    @Update
    suspend fun atualizar(exercicio: Exercicio)

    @Delete
    suspend fun deletar(exercicio: Exercicio)

    @Query("SELECT * FROM exercicios ORDER BY nome ASC")
    fun listarTodos(): Flow<List<Exercicio>>
}