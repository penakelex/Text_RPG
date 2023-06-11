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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.Main.TopPanelParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.CapabilitiesFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.CharacteristicsFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.HealthAndOtherInformationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.InventoryFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.SkillsFragment;
import penakelex.textRPG.homeland.databinding.ActivityPersonBinding;

/**
 * PersonActivity
 * Активность, содержащая всё о персонаже
 */
public class PersonActivity extends TopPanelParentActivity {
    private ActivityPersonBinding binding;

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Top_Panel_Activity, 1).apply();
        handlingToolBar(toolbar);
        toolbar.setNavigationOnClickListener(listener -> onNavigationClick());
        //Информация об уровне персонажа
        binding.level.setText(String.format("%s %d. %s %d. %s %d.", getResources().getString(R.string.ur_level), sharedPreferences.getInt(Level, 0), getResources().getString(R.string.exp), sharedPreferences.getInt(Experience, 0), getResources().getString(R.string.till_next_level), getUntilNextLevelXP(sharedPreferences.getInt(Experience, 0))));
        settingStartFragment();
        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                //Установка фрагмента с характеристиками персонажа
                case R.id.characteristic -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CharacteristicsFragment()).commit();
                    return true;
                }
                //Установка фрагмента с навыками персонажа
                case R.id.skills -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new SkillsFragment()).commit();
                    return true;
                }
                //Установка фрагмента со способностями персонажа
                case R.id.capabilities -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CapabilitiesFragment()).commit();
                    return true;
                }
                //Установка фрагмента с инвентарём персонажа
                case R.id.items -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new InventoryFragment()).commit();
                    return true;
                }
                //Установка фрагмента со статусами здоровья персонажа игрока и другой информацией
                case R.id.healthStatus_Others -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new HealthAndOtherInformationFragment()).commit();
                    return true;
                }
            }
            return false;
        });
    }

    /** onNavigationClick - процедура
     *      Возвращает назад при нажатии на кнопку
     * */
    private void onNavigationClick() {
        binding = null;
        onBackPressed();
        finish();
    }

    /** getUntilNextLevelXP - функция
     *      Сколько до следующего уровня осталось опыта
     *  @param XP - весь опыт
     *  @return 1000 - XP - оставшийся опыт до следующего уровня
     * */
    private int getUntilNextLevelXP(int XP) {
        while (XP > 1000) XP -= 1000;
        return 1000 - XP;
    }

    /** settingStartFragment - процедура
     *      Установка стартого фрагмента
     * */
    private void settingStartFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerForPerson, new CharacteristicsFragment()).commit();
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