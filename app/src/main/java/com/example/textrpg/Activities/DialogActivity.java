package com.example.textrpg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.textrpg.Dialogs;
import com.example.textrpg.databinding.ActivityDialogActvivityBinding;
import com.example.textrpg.databinding.ReplicaButtonBinding;

public class DialogActivity extends AppCompatActivity {
    private final String ID_Dialog = "ID dialog", Reputation = "Reputation", Money = "Money",
            Starting_To_Creating_Character = "Starting To Creating Character",
            Main_Character_Name = "Main Character Name",Gender = "Gender",
            Main_Character_Age = "Main Character Age", Main_Character_Height = "Main Character Height";
    ;
    Dialogs dialogs = new Dialogs();
    SharedPreferences sharedPreferences;
    private ActivityDialogActvivityBinding binding;
    private Dialogs.Quote[] quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogActvivityBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        initiateDialog(getPreferences(MODE_PRIVATE).getInt(ID_Dialog, 0));
    }

    private void initiateDialog(int ID) {
        quotes = dialogs.getQuotes(ID);
        startQuote(0);
    }

    private void startQuote(int step) {
        switch (step) {
            case -1:
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                break;
            case -2: startActivity(new Intent(DialogActivity.this, Map.class));
            default:
                fillReplicas(quotes[step]);

        }
    }

    private void fillReplicas(Dialogs.Quote quote) {
        binding.containerForReplicasVariants.removeAllViews();
        binding.name.setText(quote.getName());
        binding.text.setText(quote.getQuote());
        for (Dialogs.Quote.CharacterQuote characterQuote : quote.getCharacterQuotes()) {
            ReplicaButtonBinding buttonBinding = ReplicaButtonBinding.inflate(getLayoutInflater(), binding.containerForReplicasVariants, false);
            buttonBinding.getRoot().setText(characterQuote.getQuote());
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    private void replicaListener(Dialogs.Quote.CharacterQuote characterQuote) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if (characterQuote.getMoney() != 0 || characterQuote.getReputation() != 0) {
            String checkingParameter = characterQuote.getChecking();
            if (!checkingParameter.equals("")) {
                if (sharedPreferences.getInt(checkingParameter, 0) >= characterQuote.getCheckingValue()) {
                    sharedPreferences.edit().
                            putInt(Reputation, sharedPreferences.getInt(Reputation, 0)
                                    + characterQuote.getReputation()).
                            putInt(Money, sharedPreferences.getInt(Money, 0)
                                    + characterQuote.getMoney()).apply();
                }
            } else {
                sharedPreferences.edit().
                        putInt(Reputation, sharedPreferences.getInt(Reputation, 0)
                                + characterQuote.getReputation()).
                        putInt(Money, sharedPreferences.getInt(Money, 0)
                                + characterQuote.getMoney()).apply();
            }
        }
        startQuote(characterQuote.getNextStep());
    }
}