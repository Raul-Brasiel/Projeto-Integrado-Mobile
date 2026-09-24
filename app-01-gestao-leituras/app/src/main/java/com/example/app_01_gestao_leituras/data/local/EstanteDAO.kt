package com.example.app_01_gestao_leituras.data.local
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Transaction
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.EstanteWithLogs
import com.example.app_01_gestao_leituras.model.ReadingLogEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface EstanteDAO {
    @Query("SELECT * FROM livros")
    fun getAllBooks(): Flow<List<Estante>>

    @Insert
    suspend fun insertBook(estante: Estante)

    @Update
    suspend fun updateBook(estante: Estante)

    @Delete
    suspend fun deleteBook(estante: Estante)

    @Transaction
    @Query("SELECT * FROM livros")
    fun getBooksWithLogs(): Flow<List<EstanteWithLogs>>

    @Insert
    suspend fun insertLog(log: ReadingLogEntity)

    @Update
    suspend fun updateLog(log: ReadingLogEntity)

    @Delete
    suspend fun deleteLog(log: ReadingLogEntity)
}