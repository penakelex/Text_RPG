package penakelex.textRPG.homeland.Dialogs;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Main.Constants.Money;
import static penakelex.textRPG.homeland.Main.Constants.Reputation;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 3).apply();
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
                binding = null;
                CharacteristicsDatabase.settingStartValuesInDatabase(new CharacteristicsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new String[]{getResources().getString(R.string.strength),getResources().getString(R.string.physique), getResources().getString(R.string.dexterity), getResources().getString(R.string.mentality), getResources().getString(R.string.luckiness), getResources().getString(R.string.watchfulness), getResources().getString(R.string.attractiveness)});
                SkillsDatabase.settingStartingSkillsInDatabase(new SkillsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getApplicationContext()).getReadableDatabase(), new String[]{getResources().getString(R.string.lightWeapons), getResources().getString(R.string.heavyWeapons), getResources().getString(R.string.meleeWeapons), getResources().getString(R.string.communication), getResources().getString(R.string.trading), getResources().getString(R.string.survival), getResources().getString(R.string.medicine), getResources().getString(R.string.scince), getResources().getString(R.string.repair)});
                TalentsDatabase.settingStartingValuesInDatabase(new TalentsDatabaseHelper(getApplicationContext()).getWritableDatabase(), new String[]{getResources().getString(R.string.singer), getResources().getString(R.string.bull), getResources().getString(R.string.strong_kick), getResources().getString(R.string.experienced), getResources().getString(R.string.trained), getResources().getString(R.string.heavyweight),getResources().getString(R.string.kind_one)});
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            case -2:
                binding = null;
                sharedPreferences.edit().putInt(Global_Map_Location, 2).putBoolean(Static_Position, true).putInt(Local_Map_Location, 51).apply();
                startActivity(new Intent(DialogActivity.this, Map.class));
                finish();
                break;
            case -3:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, true).
                        putInt(ID_Dialog, 2).apply();
                binding = null;
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            case -4:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, false).
                        putInt(ID_Dialog, 3).apply();
                QuestsDatabase.getDatabase(getApplicationContext()).questsDao().updateQuestStage((short) 2, 1);
                binding = null;
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            default:
                fillReplicas(quotes[step]);
        }
    }


    private void fillReplicas(Dialogs.Quote quote) {
        binding.containerForReplicasVariants.removeAllViews();
        settingTalkingCharacterImage(quote.getImage());
        settingTalkingCharacterName(quote.getName());
        settingTalkingCharacterQuote(quote.getQuote());
        for (Dialogs.Quote.CharacterQuote characterQuote : quote.getCharacterQuotes()) {
            ReplicaButtonBinding buttonBinding = ReplicaButtonBinding.inflate(getLayoutInflater(), binding.containerForReplicasVariants, false);
            buttonBinding.getRoot().setText(getResources().getString(characterQuote.getQuote()));
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    private void settingTalkingCharacterName(int name) {
        binding.name.setText(name);
    }

    private void settingTalkingCharacterQuote(int quote) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (quote) {
            case 1: {
                binding.text.setText(String.format("Ваше имя - %s.", sharedPreferences.getString(Main_Character_Name, "")));
            }
            break;
            default:
                binding.text.setText(String.valueOf(getResources().getString(quote)));
        }
    }


    private void replicaListener(Dialogs.Quote.CharacterQuote characterQuote) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if (characterQuote.getMoney() != 0 || characterQuote.getReputation() != 0) {
            int checkingParameter = characterQuote.getChecking();
            if (checkingParameter != -1) {
                int checkingValue;
                if (checkingParameter >= 1 && checkingParameter <= 7) {
                    checkingValue = CharacteristicsDatabase.getValue(new CharacteristicsDatabaseHelper(getApplicationContext()).getReadableDatabase(), checkingParameter);

                } else if (checkingParameter >= 8 && checkingParameter <= 16) {
                    checkingParameter -= 7;
                    checkingValue = SkillsDatabase.getValueSkill(new SkillsDatabaseHelper(getApplicationContext()).getReadableDatabase(), checkingParameter);
                } else {
                    checkingParameter -= 16;
                    checkingValue = TalentsDatabase.isHaving(checkingParameter, new SkillsDatabaseHelper(getApplicationContext()).getReadableDatabase()) ? 1 : 0;
                }
                if (checkingValue >= characterQuote.getCheckingValue()) {
                    sharedPreferences.edit().
                            putInt(Reputation, sharedPreferences.getInt(Reputation, 0)
                                    + characterQuote.getReputation()).
                            putInt(Money, sharedPreferences.getInt(Money, 0)
                                    + characterQuote.getMoney()).apply();
                }
            }
        }
        startQuote(characterQuote.getNextStep());
    }

    private void settingTalkingCharacterImage(int image) {
        switch (image) {
            case 1:
                binding.imageOfCharacter.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.registrator));
                break;
            case 2:
                binding.imageOfCharacter.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.instructor_serdcev));
                break;
            default:
                binding.imageOfCharacter.setImageDrawable(null);
                break;
        }
    }
}