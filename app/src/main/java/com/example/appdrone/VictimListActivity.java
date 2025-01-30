package com.example.appdrone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class VictimListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VictimAdapter victimAdapter;
    private List<Victim> victimList;
    private MaterialToolbar toolbar;
    private FirebaseFirestore db; // FirebaseFirestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerView);
        victimList = new ArrayList<>();
        victimAdapter = new VictimAdapter(this, victimList);
        recyclerView.setAdapter(victimAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance(); // Initialize FirebaseFirestore

        victimAdapter.setOnItemClickListener(victim -> {
            // Handle item clicks (e.g., opening MapVictim)
            Intent intent = new Intent(VictimListActivity.this, MapVictim.class);
            intent.putExtra("victims", (ArrayList<Victim>) victimList);
            startActivity(intent);
        });

        // Retrieve data from Firebase and set toggle button state
        FirebaseDatabase.getInstance().getReference("victimes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        victimList.clear();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Victim victim = childSnapshot.getValue(Victim.class);
                            if (victim != null) {
                                victimList.add(victim);
                            }
                        }
                        victimAdapter.setVictims(victimList);
                        victimAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("VictimListActivity", "Database error: " + databaseError.getMessage());
                        Toast.makeText(VictimListActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}