package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.createNewOne.setOnClickListener(listener -> staringNewGame());
        binding.load.setOnClickListener(l -> settingLastActivity());
        binding.exit.setOnClickListener(l -> this.finishAffinity());
        binding.credits.setOnClickListener(l -> startActivity(new Intent(MainMenu.this, Credits.class)));
    }

    private void staringNewGame() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Is_Game_Started, false)) {
            new StartingNewGameWithProgress().show(getFragmentManager().beginTransaction(), "new or not");
        } else {
            sharedPreferences.edit().putInt(ID_Dialog, 0).putBoolean(Is_Game_Started, true).apply();
            QuestsDatabase.getDatabase(getApplicationContext()).questsDao().addQuest(new Quest(getResources().getString(R.string.quest_registration)));
            startActivity(new Intent(MainMenu.this, DialogActivity.class));
            finish();
        }
    }

    private void settingLastActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (sharedPreferences.getInt(Current_Activity, 0)) {
            case 1:
                startActivity(new Intent(this, CreatingCharacter.class));
                finish();
                break;
            case 2:
                startActivity(new Intent(this, Map.class));
                finish();
                break;
            case 3:
                startActivity(new Intent(this, DialogActivity.class));
                finish();
                break;
            default:
                Snackbar.make(binding.getRoot(),
                                getResources().getString(R.string.you_havent_started_game_yet),
                                Snackbar.LENGTH_SHORT).
                        setTextColor(getResources().getColor(R.color.golden_yellow)).
                        setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
}