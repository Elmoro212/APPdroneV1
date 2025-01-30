package com.example.appdrone;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    protected boolean showBackButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar); // Assurez-vous que votre layout a un Toolbar avec l'ID "my_toolbar"
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // Afficher le bouton "retour" si ce n'est pas MainActivity
            if (showBackButton) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Gérer le bouton "retour"
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}