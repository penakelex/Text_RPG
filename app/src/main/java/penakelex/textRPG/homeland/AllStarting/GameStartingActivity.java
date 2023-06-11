package penakelex.textRPG.homeland.AllStarting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import penakelex.textRPG.homeland.databinding.ActivityGameStartingBinding;


public class GameStartingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGameStartingBinding binding = ActivityGameStartingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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