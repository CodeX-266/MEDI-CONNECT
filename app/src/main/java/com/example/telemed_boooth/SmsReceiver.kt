package com.example.telemed_boooth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Handler
import android.os.Looper
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val pdus = bundle["pdus"] as? Array<*>
            val format = bundle.getString("format") // For API >= 23

            pdus?.forEach { pdu ->
                try {
                    val sms = if (format != null) {
                        SmsMessage.createFromPdu(pdu as ByteArray, format)
                    } else {
                        SmsMessage.createFromPdu(pdu as ByteArray)
                    }

                    val messageBody = sms.messageBody
                    Log.d(TAG, "Received SMS: $messageBody")

                    // Expecting format: "UPDATE:Paracetamol:0"
                    if (messageBody.startsWith("UPDATE:")) {
                        val parts = messageBody.split(":")
                        if (parts.size == 3) {
                            val medName = parts[1].trim()
                            val qty = parts[2].trim().toIntOrNull() ?: 0

                            // Update database on background thread
                            CoroutineScope(Dispatchers.IO).launch {
                                val db = AppDatabase.getDatabase(context)
                                val dao = db.medicineDao()
                                val med = dao.getMedicineByName(medName)
                                if (med != null) {
                                    val updatedMed = med.copy(
                                        quantity = qty,
                                        available = qty > 0
                                    )
                                    dao.update(updatedMed)

                                    // Show Toast on main thread
                                    Handler(Looper.getMainLooper()).post {
                                        Toast.makeText(
                                            context,
                                            "Stock updated: $medName ($qty)",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Log.d(TAG, "Medicine not found in DB: $medName")
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error reading SMS", e)
                }
            }
        }
    }
}
