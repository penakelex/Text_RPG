package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.textrpg.databinding.ActivityCreditsBinding;

public class Credits extends AppCompatActivity {
    private ActivityCreditsBinding activityCreditsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreditsBinding = ActivityCreditsBinding.inflate(getLayoutInflater());
        setContentView(activityCreditsBinding.getRoot());
        activityCreditsBinding.exitButton.setOnClickListener(l -> {
            Intent intent = new Intent(Credits.this, MainMenu.class);
            startActivity(intent);
        });
    }
}