package com.example.app_02_treinos_performance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app_02_treinos_performance.data.local.ExercicioDAO
import com.example.app_02_treinos_performance.data.local.FichaDAO
import com.example.app_02_treinos_performance.data.local.ItemFichaDAO
import com.example.app_02_treinos_performance.data.model.Serie
import com.example.app_02_treinos_performance.data.local.SerieDAO
import com.example.app_02_treinos_performance.data.model.Cardio
import com.example.app_02_treinos_performance.data.local.CardioDAO
import com.example.app_02_treinos_performance.data.model.Exercicio
import com.example.app_02_treinos_performance.data.model.Ficha
import com.example.app_02_treinos_performance.data.model.ItemFicha
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar

@Database(
    entities = [Ficha::class, Exercicio::class, ItemFicha::class, Serie::class, Cardio::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fichaDao(): FichaDAO
    abstract fun exercicioDao(): ExercicioDAO
    abstract fun itemFichaDao(): ItemFichaDAO
    abstract fun serieDao(): SerieDAO
    abstract fun cardioDao(): CardioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "treinos_db")
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch {
                                INSTANCE?.let { popularBancoComDadosDeTeste(it) }
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

private suspend fun popularBancoComDadosDeTeste(database: AppDatabase) {
    val exercicioDao = database.exercicioDao()
    val fichaDao = database.fichaDao()
    val itemFichaDao = database.itemFichaDao()
    val cardioDao = database.cardioDao()

    val supinoId = exercicioDao.inserir(Exercicio(nome = "Supino Reto", grupoMuscular = "Peito"))
    val agachamentoId = exercicioDao.inserir(Exercicio(nome = "Agachamento", grupoMuscular = "Perna"))
    val roscaId = exercicioDao.inserir(Exercicio(nome = "Rosca Direta", grupoMuscular = "Bíceps"))
    val remadaId = exercicioDao.inserir(Exercicio(nome = "Remada Curvada", grupoMuscular = "Costas"))

    val fichaAId = fichaDao.inserir(Ficha(nome = "Treino A"))
    itemFichaDao.adicionarExercicioNaFicha(ItemFicha(fichaId = fichaAId, exercicioId = supinoId, series = 4, repeticoes = 10, cargaKg = 40f, ordem = 0))
    itemFichaDao.adicionarExercicioNaFicha(ItemFicha(fichaId = fichaAId, exercicioId = remadaId, series = 4, repeticoes = 10, cargaKg = 35f, ordem = 1))

    val fichaBId = fichaDao.inserir(Ficha(nome = "Treino B"))
    itemFichaDao.adicionarExercicioNaFicha(ItemFicha(fichaId = fichaBId, exercicioId = agachamentoId, series = 4, repeticoes = 12, cargaKg = 60f, ordem = 0))
    itemFichaDao.adicionarExercicioNaFicha(ItemFicha(fichaId = fichaBId, exercicioId = roscaId, series = 3, repeticoes = 12, cargaKg = 15f, ordem = 1))

    fun dataHaDiasAtras(dias: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -dias)
        return calendar.timeInMillis
    }

    cardioDao.inserir(Cardio(nome = "Esteira", distanciaKm = 6f, tempoSegundos = 35 * 60, dataEpochMillis = dataHaDiasAtras(0)))
    cardioDao.inserir(Cardio(nome = "Caminhada", distanciaKm = 9.2f, tempoSegundos = 56 * 60, dataEpochMillis = dataHaDiasAtras(3)))
    cardioDao.inserir(Cardio(nome = "Corrida", distanciaKm = 6.8f, tempoSegundos = 39 * 60 + 54, dataEpochMillis = dataHaDiasAtras(6)))
}