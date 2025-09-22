package com.example.telemed_boooth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Include both Patient and Medicine entities
@Database(entities = [Patient::class, Medicine::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun patientDao(): PatientDao
    abstract fun medicineDao(): MedicineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "telemed_database"
                )
                    // Prevent crashes if schema changes (will wipe old data)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
