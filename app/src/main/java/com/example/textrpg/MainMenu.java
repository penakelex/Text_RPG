package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.textrpg.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createNewOne.setOnClickListener(l -> {
            Intent intent = new Intent(MainMenu.this, GameLoad.class);
            startActivity(intent);
        });
        binding.load.setOnClickListener(l -> {
            Intent intent = new Intent(MainMenu.this, GameLoad.class);
            startActivity(intent);
        });
        binding.exit.setOnClickListener(l -> this.finishAffinity());
        binding.credits.setOnClickListener(l -> {
            Intent intent = new Intent(MainMenu.this, Credits.class);
            startActivity(intent);
        });
    }
}