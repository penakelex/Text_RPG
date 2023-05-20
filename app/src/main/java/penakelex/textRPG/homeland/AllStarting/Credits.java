package penakelex.textRPG.homeland.AllStarting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import penakelex.textRPG.homeland.databinding.ActivityCreditsBinding;


public class Credits extends AppCompatActivity {
    private ActivityCreditsBinding activityCreditsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreditsBinding = ActivityCreditsBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(activityCreditsBinding.getRoot());
        activityCreditsBinding.exitButton.setOnClickListener(l -> {
            activityCreditsBinding = null;
            startActivity(new Intent(Credits.this, MainMenu.class));
            finish();
        });
    }
}