package penakelex.textRPG.homeland.CreatingCharacterForm;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments.StartingInfoFragment;
import penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments.StartingInformationFragment;
import penakelex.textRPG.homeland.databinding.ActivityCreatingCharacterBinding;

public class CreatingCharacter extends MainActionParentActivity {
    private ActivityCreatingCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingCharacterBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        sharedPreferences.edit().putInt(Current_Activity, 1).apply();
        if (sharedPreferences.getBoolean(Going_To_Starting_Information, true)) {
            getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInfoFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}