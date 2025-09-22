package com.example.telemed_boooth

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicines: List<Medicine>)

    // ✅ Extra version for Java (not suspend)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSync(medicines: List<Medicine>)

    @Query("SELECT * FROM medicines")
    fun getAllMedicines(): LiveData<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE name = :medicineName LIMIT 1")
    suspend fun getMedicineByName(medicineName: String): Medicine?

    @Update
    suspend fun update(medicine: Medicine)

    @Query("UPDATE medicines SET available = :status WHERE name = :medicineName")
    suspend fun updateAvailability(medicineName: String, status: Boolean)

    @Query("UPDATE medicines SET quantity = :stock WHERE name = :medicineName")
    suspend fun updateStock(medicineName: String, stock: Int)

    @Query("DELETE FROM medicines")
    suspend fun clearAll()
}
