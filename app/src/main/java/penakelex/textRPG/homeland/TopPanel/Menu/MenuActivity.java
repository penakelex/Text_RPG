package penakelex.textRPG.homeland.TopPanel.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import penakelex.textRPG.homeland.AllStarting.MainMenu;
import penakelex.textRPG.homeland.Main.ActionParentActivity;
import penakelex.textRPG.homeland.databinding.ActivityMenuBinding;

public class MenuActivity extends ActionParentActivity {
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.exitGame.setOnClickListener(listener -> this.finishAffinity());
        binding.backToGame.setOnClickListener(listener -> backToLastActivity());
        binding.backToMainMenu.setOnClickListener(listener -> goingToMainMenu());
        binding.settings.setOnClickListener(listener -> startingSetting());
    }

    private void startingSetting() {
        finish();
    }

    private void backToLastActivity() {
        onBackPressed();
        finish();
    }

    private void goingToMainMenu() {
        //TODO: Сделать диалоговое окно
        startActivity(new Intent(MenuActivity.this, MainMenu.class));
    }

}