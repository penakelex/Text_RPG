package penakelex.textRPG.homeland.Dialogs;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Main.Constants.Trader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Arrays;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.BooleanSP;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.DialogActivityHelper;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.IntSP;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.ShortItemInformation;
import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.Trading.TradingActivity;
import penakelex.textRPG.homeland.databinding.ActivityDialogBinding;
import penakelex.textRPG.homeland.databinding.ReplicaButtonBinding;

public class DialogActivity extends MainActionParentActivity {
    private final Dialogs dialogs = new Dialogs();
    private ActivityDialogBinding binding;
    private Dialogs.Quote[] quotes;
    private DialogActivityHelper dialogActivityHelper;
    private final int[] array = {0, 0, 0}; //репутация, опыт, деньги
    private final ArrayList<ShortItemInformation> itemsToAdd = new ArrayList<>();
    private final short[] plusStatistics = new short[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        dialogActivityHelper = new DialogActivityHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
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
        switch (step) {
            case -1 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, false));
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(ID_Dialog, 1));
                saveChanges();
                binding = null;
                goingToCreatingCharacter();
            }
            case -2 -> {
                dialogActivityHelper.updateLocationForDialog(4, 2, 24, false);
                saveChanges();
                goingToMap();
                binding = null;
            }
            case -3 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, true), new BooleanSP(Is_Going_To_Starting_Information_First_Time, true));
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(ID_Dialog, 2));
                saveChanges();
                binding = null;
                goingToCreatingCharacter();
            }
            case -4 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, true), new BooleanSP(Is_Going_To_Starting_Information_First_Time, false));
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(Experience, 200), new IntSP(ID_Dialog, 3));
                saveChanges();
                dialogActivityHelper.updateQuestStage((short) 2, (short) 1);
                binding = null;
                goingToCreatingCharacter();
            }
            case -5 -> initiateDialog(4);
            case -6 -> {
                dialogActivityHelper.addQuest(R.string.excursion);
                dialogActivityHelper.updateLocationForDialog(5, 1, 20, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -7 -> {
                dialogActivityHelper.updateLocationForDialog(6, 2, 24, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -8 -> {
                dialogActivityHelper.updateLocationForDialog(7, 6, 43,  true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -9 -> {
                itemsToAdd.add(new ShortItemInformation((short) 1));
                fillReplicas(quotes[3]);
            }
            case -10 -> {
                dialogActivityHelper.updateLocationForDialog(8, 6, 44, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -11 -> {
                saveChanges();
                dialogActivityHelper.updateQuestStage((short) 2, (short) 2);
                binding = null;
                dialogActivityHelper.updateLocationForDialog(9, 6, 44, false);
                goingToMap();
            }
            case -12 -> {
                saveChanges();
                binding = null;
                dialogActivityHelper.updateLocationForDialog(10, 2, 24, false);
                goingToMap();
            }
            case -13 -> {
                dialogActivityHelper.updateLocationForDialog(12, 1, 20, false);
                saveChanges();
                initiateDialog(12);
            }
            case -14 -> {
                dialogActivityHelper.updateLocationForDialog(11, 2, 24, false);
                saveChanges();
                initiateDialog(11);
            }
            case -15 -> {
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(Trader, 2));
                for (int i = 0; i < 10; i++) {
                    itemsToAdd.add(new ShortItemInformation((short) 2, (short) 2));
                }
                saveChanges();
                binding = null;
                goingToTrading();
            }
            case -16 -> {
                dialogActivityHelper.updateLocationForDialog(12, 2, 24, false);
                saveChanges();
            }
            default -> fillReplicas(quotes[step]);
        }
    }

    private void goingToTrading() {
        startActivity(new Intent(this, TradingActivity.class));
        finish();
    }

    private void saveChanges() {
        dialogActivityHelper.saveChanges(array, itemsToAdd, plusStatistics, binding.getRoot(), getApplicationContext());
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
            buttonBinding.getRoot().setText(dialogActivityHelper.getSettingValueForCharacterQuote(characterQuote.getQuote(), getApplicationContext()));
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    private void settingTalkingCharacterQuote(int quote) {
        binding.text.setText(dialogActivityHelper.getTalkingCharacterQuote(quote, getApplicationContext()));
    }

    private void replicaListener(Dialogs.Quote.CharacterQuote characterQuote) {
        dialogActivityHelper.replicaListener(characterQuote, array, plusStatistics);
        startQuote(characterQuote.getNextStep());
    }

    private void settingTalkingCharacterName(int name) {
        binding.name.setText(name);
    }

    private void settingTalkingCharacterImage(int image) {
        binding.imageOfCharacter.setImageDrawable(DialogActivityHelper.getTalkingCharacterImage(image, getApplicationContext()));
    }
}