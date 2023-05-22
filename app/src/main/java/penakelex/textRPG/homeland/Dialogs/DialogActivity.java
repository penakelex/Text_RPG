package penakelex.textRPG.homeland.Dialogs;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Arrays;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.StatisticsDatabase.StatisticsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabaseHelper;
import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityDialogBinding;
import penakelex.textRPG.homeland.databinding.ReplicaButtonBinding;

public class DialogActivity extends MainActionParentActivity {
    private final Dialogs dialogs = new Dialogs();
    private SharedPreferences sharedPreferences;
    private ActivityDialogBinding binding;
    private Dialogs.Quote[] quotes;
    private final int[] array = {0, 0, 0}; //репутация, опыт, деньги
    private final ArrayList<Integer> itemsToAdd = new ArrayList<>();
    private short[] plusStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 3).apply();
        plusStatistics = new short[StatisticsDatabaseHelper.getStatisticsCount(getApplicationContext())];
        initiateDialog(sharedPreferences.getInt(ID_Dialog, 0));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initiateDialog(int ID) {
        quotes = dialogs.getQuotes(ID);
        startQuote(0);
    }

    private void startQuote(int step) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (step) {
            case -1:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, false).
                        putInt(ID_Dialog, 1).apply();
                CharacteristicsDatabase.settingStartValuesInDatabase(new CharacteristicsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new String[]{getResources().getString(R.string.strength), getResources().getString(R.string.physique), getResources().getString(R.string.dexterity), getResources().getString(R.string.mentality), getResources().getString(R.string.luckiness), getResources().getString(R.string.watchfulness), getResources().getString(R.string.attractiveness)});
                SkillsDatabase.settingStartingSkillsInDatabase(new SkillsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getApplicationContext()).getReadableDatabase(), new String[]{getResources().getString(R.string.lightWeapons), getResources().getString(R.string.heavyWeapons), getResources().getString(R.string.meleeWeapons), getResources().getString(R.string.communication), getResources().getString(R.string.trading), getResources().getString(R.string.survival), getResources().getString(R.string.medicine), getResources().getString(R.string.scince), getResources().getString(R.string.repair)});
                TalentsDatabase.settingStartingValuesInDatabase(new TalentsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new String[]{getResources().getString(R.string.singer), getResources().getString(R.string.bull), getResources().getString(R.string.strong_kick), getResources().getString(R.string.experienced), getResources().getString(R.string.trained), getResources().getString(R.string.heavyweight), getResources().getString(R.string.kind_one)});
                saveChanges();binding = null;
                goingToCreatingCharacter();
                break;
            case -2:

                DialogActivityHelper.updateLocationForDialog(4, 2, 24, getApplicationContext(), false);
                saveChanges();
                goingToMap();
                binding = null;
                break;
            case -3:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, true).
                        putInt(ID_Dialog, 2).apply();
                saveChanges();
                binding = null;
                goingToCreatingCharacter();
                break;
            case -4:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, false).
                        putInt(Experience, 200).
                        putInt(ID_Dialog, 3).apply();
                saveChanges();
                QuestsDatabase.getDatabase(getApplicationContext()).questsDao().updateQuestStage((short) 2, 1);
                binding = null;
                goingToCreatingCharacter();
                break;
            case -5:
                initiateDialog(4);
                break;
            case -6:
                QuestsDatabase.getDatabase(getApplicationContext()).questsDao().addQuest(new Quest(getResources().getString(R.string.excursion)));
                DialogActivityHelper.updateLocationForDialog(5, 1, 20, getApplicationContext(), true);
                saveChanges();
                binding = null;
                goingToMap();
                break;
            case -7:
                DialogActivityHelper.updateLocationForDialog(6, 2, 24, getApplicationContext(), true);
                saveChanges();
                binding = null;
                goingToMap();
                break;
            case -8:
                DialogActivityHelper.updateLocationForDialog(7, 6, 43, getApplicationContext(), true);
                saveChanges();
                binding = null;
                goingToMap();
                break;
            case -9:
                itemsToAdd.add(1);
                fillReplicas(quotes[3]);
                break;
            case -10:
                DialogActivityHelper.updateLocationForDialog(8, 6, 44, getApplicationContext(), true);
                saveChanges();
                binding = null;
                goingToMap();
                break;
            case -11:
                saveChanges();
                QuestsDatabase.getDatabase(getApplicationContext()).questsDao().updateQuestStage((short) 2, 2);
                binding = null;
                DialogActivityHelper.updateLocationForDialog(9, 6, 44, getApplicationContext(), false);
                goingToMap();
                break;
            case -12:
                binding = null;
                DialogActivityHelper.updateLocationForDialog(10, 2, 24, getApplicationContext(), false);
                goingToMap();
                break;
            default:
                fillReplicas(quotes[step]);
        }
    }

    private void saveChanges() {
        DialogActivityHelper.saveChanges(array, itemsToAdd, plusStatistics, binding.getRoot(), getApplicationContext());
        Arrays.fill(array, 0);
        itemsToAdd.clear();
        Arrays.fill(plusStatistics, (short) 0);
    }

    private void goingToMap() {
        startActivity(new Intent(DialogActivity.this, Map.class));
        finish();
    }

    private void goingToCreatingCharacter() {
        startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
        finish();
    }

    private void fillReplicas(Dialogs.Quote quote) {
        binding.containerForReplicasVariants.removeAllViews();
        settingTalkingCharacterImage(quote.getImage());
        settingTalkingCharacterName(quote.getName());
        settingTalkingCharacterQuote(quote.getQuote());
        for (Dialogs.Quote.CharacterQuote characterQuote : quote.getCharacterQuotes()) {
            ReplicaButtonBinding buttonBinding = ReplicaButtonBinding.inflate(getLayoutInflater(), binding.containerForReplicasVariants, false);
            buttonBinding.getRoot().setText(DialogActivityHelper.getSettingValueForCharacterQuote(characterQuote.getQuote(), getApplicationContext()));
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    private void settingTalkingCharacterQuote(int quote) {
        binding.text.setText(DialogActivityHelper.getTalkingCharacterQuote(quote, getApplicationContext()));
    }

    private void replicaListener(Dialogs.Quote.CharacterQuote characterQuote) {
        DialogActivityHelper.replicaListener(characterQuote, array, plusStatistics, getApplicationContext());
        startQuote(characterQuote.getNextStep());
    }

    private void settingTalkingCharacterName(int name) {
        binding.name.setText(name);
    }

    private void settingTalkingCharacterImage(int image) {
        binding.imageOfCharacter.setImageDrawable(DialogActivityHelper.getTalkingCharacterImage(image, getApplicationContext()));
    }
}