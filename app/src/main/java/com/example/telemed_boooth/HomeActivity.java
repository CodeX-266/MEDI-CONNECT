package com.example.telemed_boooth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMedicines;
    private Button btnLogout;
    private MedicineAdapter medicineAdapter;

    private static final int SMS_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvMedicines = findViewById(R.id.rvMedicines);
        btnLogout = findViewById(R.id.btnLogout);

        // Setup RecyclerView
        medicineAdapter = new MedicineAdapter(new ArrayList<>());
        rvMedicines.setAdapter(medicineAdapter);
        rvMedicines.setLayoutManager(new LinearLayoutManager(this));

        // Observe database for real-time updates
        AppDatabase db = AppDatabase.getDatabase(this);
        db.medicineDao().getAllMedicines().observe(this, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(List<Medicine> medicines) {
                medicineAdapter.updateMedicines(medicines);

                // If database is empty, preload medicines
                if (medicines == null || medicines.isEmpty()) {
                    preloadMedicines(db);
                }
            }
        });

        // Request SMS permissions
        requestSmsPermissions();

        // Logout button
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void requestSmsPermissions() {
        String[] permissions = {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS
        };

        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsToRequest.toArray(new String[0]),
                    SMS_PERMISSION_REQUEST);
        }
    }

    // Insert default medicines if DB is empty
    private void preloadMedicines(AppDatabase db) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Medicine> defaultMeds = new ArrayList<>();
            defaultMeds.add(new Medicine(0, "Paracetamol", 20, true));
            defaultMeds.add(new Medicine(0, "Ibuprofen", 0, false));
            defaultMeds.add(new Medicine(0, "Amoxicillin", 15, true));
            defaultMeds.add(new Medicine(0, "Cough Syrup", 0, false));
            defaultMeds.add(new Medicine(0, "Vitamin D", 30, true));

            // Synchronously insert medicines for Java
            db.medicineDao().insertAllSync(defaultMeds);
        });
    }
}
