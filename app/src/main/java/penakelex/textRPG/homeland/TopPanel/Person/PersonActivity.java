package penakelex.textRPG.homeland.TopPanel.Person;


import static penakelex.textRPG.homeland.Main.Constants.Current_Top_Panel_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Level;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.Main.TopPanelParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.CapabilitiesFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.CharacteristicsFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.HealthAndOtherInformationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.InventoryFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.SkillsFragment;
import penakelex.textRPG.homeland.databinding.ActivityPersonBinding;

public class PersonActivity extends TopPanelParentActivity {
    private ActivityPersonBinding binding;
    private SharedPreferences sharedPreferences;

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Top_Panel_Activity, 1).apply();
        handlingToolBar(toolbar);
        toolbar.setNavigationOnClickListener(listener -> onNavigationClick());
        binding.level.setText(String.format("Ваш уровень: %d. Опыт: %d. До следующего уровня: %d.", sharedPreferences.getInt(Level, 0), sharedPreferences.getInt(Experience, 0), getUntilNextLevelXP(sharedPreferences.getInt(Experience, 0))));
        settingStartFragment();
        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.characteristic:
                    getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CharacteristicsFragment()).commit();
                    return true;
                case R.id.skills:
                    getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new SkillsFragment()).commit();
                    return true;
                case R.id.capabilities:
                    getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CapabilitiesFragment()).commit();
                    return true;
                case R.id.items:
                    getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new InventoryFragment()).commit();
                    return true;
                case R.id.healthStatus_Others:
                    getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new HealthAndOtherInformationFragment()).commit();
                    return true;
            }
            return false;
        });
    }

    private void onNavigationClick() {
        binding = null;
        onBackPressed();
        finish();
    }

    private int getUntilNextLevelXP(int XP) {
        while (XP > 1000) XP -= 1000;
        return 1000 - XP;
    }

    private void settingStartFragment() {
        getFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CharacteristicsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}