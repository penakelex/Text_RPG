package penakelex.textRPG.homeland.AllStarting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import penakelex.textRPG.homeland.R;


public class GameStartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_game_starting);
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(250);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    startActivity(new Intent(GameStartingActivity.this, MainMenu.class));
                    finish();
                }
            }
        };
        thread.start();*/
        startActivity(new Intent(GameStartingActivity.this, MainMenu.class));
        finish();
    }
}