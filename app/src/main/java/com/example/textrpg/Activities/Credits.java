package com.example.textrpg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.example.textrpg.databinding.ActivityCreditsBinding;

public class Credits extends AppCompatActivity {
    private ActivityCreditsBinding activityCreditsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreditsBinding = ActivityCreditsBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(activityCreditsBinding.getRoot());
        activityCreditsBinding.exitButton.setOnClickListener(l -> {
            Intent intent = new Intent(Credits.this, MainMenu.class);
            startActivity(intent);
        });
    }
}