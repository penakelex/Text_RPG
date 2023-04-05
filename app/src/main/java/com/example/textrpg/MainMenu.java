package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.textrpg.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;
    private final String ID_Dialog = "ID dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createNewOne.setOnClickListener(l -> staringNewGame());
        binding.load.setOnClickListener(l -> startActivity(new Intent(MainMenu.this, GameLoad.class)));
        binding.exit.setOnClickListener(l -> this.finishAffinity());
        binding.credits.setOnClickListener(l -> startActivity(new Intent(MainMenu.this, Credits.class)));
        binding.map.setOnClickListener(listener -> startActivity(new Intent(MainMenu.this, Map.class)));
    }

    private void staringNewGame() {
        String Is_Game_Started = "Game Started";
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Is_Game_Started, false))
            startActivity(new Intent(MainMenu.this, StartGameWithProgress.class));
        else {
            startActivity(new Intent(MainMenu.this, DialogActivity.class));
        }
    }
}