package com.example.keepin_It_Fresh;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class FoodAddNavigation extends AppCompatActivity {
    ImageButton scannerButton, drawer_button;
    Button manualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodaddnavigation_page);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.foodaddnavnavdrawer)).openDrawer(Gravity.LEFT);
        });
        scannerButton = findViewById(R.id.scannerButton);
        manualButton = findViewById(R.id.manualButton);


        scannerButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Scanner.class);
            startActivity(intent);
        });

        manualButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddFoodManually.class);
            startActivity(intent);
        });

    }

    }
