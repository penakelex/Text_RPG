package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;
import static penakelex.textRPG.homeland.Main.Constants.S;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Databases.Database.DatabaseCallback;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityMainMenuBinding;
/** MainMenu
 *      Активность - главное меню приложения
 * */
public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createNewOne.setOnClickListener(listener -> staringNewGame());
        binding.load.setOnClickListener(l -> settingLastActivity());
        binding.exit.setOnClickListener(l -> this.finishAffinity());
        binding.credits.setOnClickListener(l -> startActivity(new Intent(MainMenu.this, Credits.class)));
    }

    /** staringNewGame - процедура
     *      Создание новой игры
     * */
    private void staringNewGame() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        //Если игрок уже создавал игру, то предостережение от случайного нажатия
        if (sharedPreferences.getBoolean(Is_Game_Started, false) || sharedPreferences.getBoolean(S, false)) {
            new StartingNewGameWithProgress().show(getFragmentManager().beginTransaction(), "new or not");
        } else {
            sharedPreferences.edit().putInt(ID_Dialog, 0).putBoolean(Is_Game_Started, true).apply();
            new DatabaseCallback(getApplication(), this);
            startActivity(new Intent(MainMenu.this, DialogActivity.class));
            finish();
        }
    }

    /** settingLastActivity - процедура
     *      Установка последней активности
     * */
    private void settingLastActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (sharedPreferences.getInt(Current_Activity, 0)) {
            //Переход на активность для создания персонажа
            case 1 -> {
                startActivity(new Intent(this, CreatingCharacter.class));
                finish();
            }
            //Переход на активность с картой
            case 2 -> {
                startActivity(new Intent(this, Map.class));
                finish();
            }
            //Переход на активность с диалогами
            case 3 -> {
                startActivity(new Intent(this, DialogActivity.class));
                finish();
            }
            //Если игрок не начинал игру
            default -> Snackbar.make(binding.getRoot(),
                            getResources().getString(R.string.you_havent_started_game_yet),
                            Snackbar.LENGTH_SHORT).
                    setTextColor(getResources().getColor(R.color.golden_yellow)).
                    setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
}