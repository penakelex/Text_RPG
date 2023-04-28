package penakelex.textRPG.homeland.Activities;

import static penakelex.textRPG.homeland.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Constants.First_Visit_Characteristics;
import static penakelex.textRPG.homeland.Constants.First_Visit_Main_Information;
import static penakelex.textRPG.homeland.Constants.First_Visit_Skills;
import static penakelex.textRPG.homeland.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Constants.ID_Dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.StartingFragments.StartingInfoFragment;
import penakelex.textRPG.homeland.StartingFragments.StartingInformationFragment;
import penakelex.textRPG.homeland.databinding.ActivityCreatingCharacterBinding;

public class CreatingCharacter extends AppCompatActivity {
    private ActivityCreatingCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingCharacterBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 1).apply();
        if (sharedPreferences.getBoolean(Going_To_Starting_Information, true)) {
            getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInfoFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }
}