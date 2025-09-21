package com.example.telemed_boooth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterPatientActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var etContact: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_patient)

        db = AppDatabase.getDatabase(this)

        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etGender = findViewById(R.id.etGender)
        etContact = findViewById(R.id.etContact)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toIntOrNull() ?: 0
            val gender = etGender.text.toString()
            val contact = etContact.text.toString()

            val patient = Patient(name = name, age = age, gender = gender, contact = contact)

            lifecycleScope.launch {
                db.patientDao().insert(patient)
                Toast.makeText(this@RegisterPatientActivity, "Patient registered!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}