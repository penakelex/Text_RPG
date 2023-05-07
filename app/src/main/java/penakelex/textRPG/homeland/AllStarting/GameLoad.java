package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import penakelex.textRPG.homeland.Map.ActivityLocalMap;
import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.databinding.ActivityGameLoadBinding;

//TODO: Сделать загрузочный экран
public class GameLoad extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ActivityGameLoadBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingLastActivity();
    }

    private void settingLastActivity() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (sharedPreferences.getInt(Current_Activity, 0)) {
            case 1:
                startActivity(new Intent(GameLoad.this, CreatingCharacter.class));
                finish();
                break;
            case 2:
                startActivity(new Intent(GameLoad.this, Map.class));
                finish();
                break;
            case 3:
                startActivity(new Intent(GameLoad.this, DialogActivity.class));
                finish();
                break;
            case 4:
                startActivity(new Intent(GameLoad.this, ActivityLocalMap.class));
                finish();
                break;
        }
    }
}