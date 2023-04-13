package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;

import java.util.logging.Handler;

public class GameStartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_starting);
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    startActivity(new Intent(GameStartingActivity.this, MainMenu.class));
                }
            }
        };
        thread.start();*/
        startActivity(new Intent(GameStartingActivity.this, MainMenu.class));
    }
}