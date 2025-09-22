package com.example.telemed_boooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(private var medicines: List<Medicine>) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvMedicineName)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvMedicineQuantity)
        val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.tvName.text = medicine.name
        holder.tvQuantity.text = "Quantity: ${medicine.quantity}"
        holder.tvAvailability.text = if (medicine.available) "Available ✅" else "Not Available ❌"
        holder.tvAvailability.setTextColor(
            if (medicine.available) 0xFF008000.toInt() else 0xFFFF0000.toInt()
        )
    }

    override fun getItemCount(): Int = medicines.size

    // ✅ Update adapter data when LiveData changes
    fun updateMedicines(newMedicines: List<Medicine>) {
        medicines = newMedicines
        notifyDataSetChanged()
    }
}
