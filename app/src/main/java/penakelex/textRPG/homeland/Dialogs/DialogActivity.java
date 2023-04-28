package penakelex.textRPG.homeland.Dialogs;

import static penakelex.textRPG.homeland.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Constants.Money;
import static penakelex.textRPG.homeland.Constants.Reputation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import penakelex.textRPG.homeland.Activities.CreatingCharacter;
import penakelex.textRPG.homeland.Activities.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityDialogActvivityBinding;
import penakelex.textRPG.homeland.databinding.ReplicaButtonBinding;

public class DialogActivity extends AppCompatActivity {
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
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 3).apply();
        initiateDialog(sharedPreferences.getInt(ID_Dialog, 0));
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
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            case -2:
                startActivity(new Intent(DialogActivity.this, Map.class));
                finish();
                break;
            case -3:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, true).
                        putInt(ID_Dialog, 2).apply();
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            case -4:
                sharedPreferences.edit().putBoolean(Going_To_Starting_Information, true).
                        putBoolean(Is_Going_To_Starting_Information_First_Time, false).
                        putInt(ID_Dialog, 3).apply();
                startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
                finish();
                break;
            default:
                fillReplicas(quotes[step]);
        }
    }

    private void fillReplicas(Dialogs.Quote quote) {
        binding.containerForReplicasVariants.removeAllViews();
        settingTalkingCharacter(quote.getImage());
        binding.name.setText(quote.getName());
        settingQuoteText(quote.getQuote());
        for (Dialogs.Quote.CharacterQuote characterQuote : quote.getCharacterQuotes()) {
            ReplicaButtonBinding buttonBinding = ReplicaButtonBinding.inflate(getLayoutInflater(), binding.containerForReplicasVariants, false);
            buttonBinding.getRoot().setText(characterQuote.getQuote());
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    private void settingQuoteText(String quote) {
        String[] quoteSymbols = quote.toString().split("");
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (quoteSymbols[quoteSymbols.length - 1]) {
            case "1": {
                StringBuilder newQuote = new StringBuilder();
                for (String symbol : quoteSymbols) {
                    if (!symbol.equals("1")) newQuote.append(symbol);
                }
                newQuote.append(sharedPreferences.getString(Main_Character_Name, "") + ".");
                binding.text.setText(newQuote.toString());
            }
            break;
            default:
                binding.text.setText(quote);
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

    private void settingTalkingCharacter(int image) {
        switch (image) {
            case 1:
                binding.imageOfCharacter.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.registrator));
                break;
            default:
                binding.imageOfCharacter.setImageDrawable(null);
                break;
        }
    }
}