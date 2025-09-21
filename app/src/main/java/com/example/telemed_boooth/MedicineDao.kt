package com.example.telemed_boooth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicineDao {

    @Insert
    suspend fun insert(medicine: Medicine)

    @Query("SELECT * FROM medicines")
    suspend fun getAllMedicines(): List<Medicine>

    @Query("UPDATE medicines SET available = :status WHERE name = :medicineName")
    suspend fun updateAvailability(medicineName: String, status: Boolean)
}
