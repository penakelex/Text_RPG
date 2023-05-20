package penakelex.textRPG.homeland.AllStarting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import penakelex.textRPG.homeland.R;


public class GameStartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_starting);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(450);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    startActivity(new Intent(GameStartingActivity.this, MainMenu.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}