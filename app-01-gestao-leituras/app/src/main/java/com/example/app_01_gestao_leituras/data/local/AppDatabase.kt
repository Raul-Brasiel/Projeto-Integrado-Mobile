
// Arquivo: data/local/AppDatabase.kt
package com.example.app_01_gestao_leituras.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.ReadingLogEntity

@Database(entities = [Estante::class, ReadingLogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun estanteDao(): EstanteDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "leituras_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}