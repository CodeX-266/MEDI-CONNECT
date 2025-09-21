package com.example.telemed_boooth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Add both Patient and Medicine entities
@Database(entities = [Patient::class, Medicine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun patientDao(): PatientDao
    abstract fun medicineDao(): MedicineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "telemed_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
