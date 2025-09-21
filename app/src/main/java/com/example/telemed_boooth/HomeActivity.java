package com.example.telemed_boooth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMedicines;
    private Button btnLogout;
    private MedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Link UI elements
        rvMedicines = findViewById(R.id.rvMedicines);
        btnLogout = findViewById(R.id.btnLogout);

        // Preloaded medicines
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine(1, "Paracetamol", 20, true));
        medicines.add(new Medicine(2, "Ibuprofen", 0, false));
        medicines.add(new Medicine(3, "Amoxicillin", 15, true));
        medicines.add(new Medicine(4, "Cough Syrup", 0, false));
        medicines.add(new Medicine(5, "Vitamin D", 30, true));

        // Setup RecyclerView
        medicineAdapter = new MedicineAdapter(medicines);
        rvMedicines.setAdapter(medicineAdapter);
        rvMedicines.setLayoutManager(new LinearLayoutManager(this));

        // Logout button
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
