package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import penakelex.textRPG.homeland.Activities.Credits;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        binding.createNewOne.setOnClickListener(l -> staringNewGame());
        binding.load.setOnClickListener(l -> loading());
        binding.exit.setOnClickListener(l -> closingGame());
        binding.credits.setOnClickListener(l -> startActivity(new Intent(MainMenu.this, Credits.class)));
        //binding.map.setOnClickListener(listener -> startActivity(new Intent(MainMenu.this, Map.class)));
    }


    private void closingGame() {
        binding = null;
        this.finishAffinity();
    }

    private void loading() {
        binding = null;
        startActivity(new Intent(MainMenu.this, GameLoad.class));
        finish();
    }

    private void staringNewGame() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Is_Game_Started, false)) {
            new StartingNewGameWithProgress().show(getFragmentManager().beginTransaction(), "new or not");
        } else {
            sharedPreferences.edit().putInt(ID_Dialog, 0).putBoolean(Is_Game_Started, true).apply();
            binding = null;
            startActivity(new Intent(MainMenu.this, DialogActivity.class));
            finish();
        }
    }
}