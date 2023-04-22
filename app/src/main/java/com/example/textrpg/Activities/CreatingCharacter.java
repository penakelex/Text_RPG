package com.example.textrpg.Activities;

import static com.example.textrpg.Constants.First_Visit_Characteristics;
import static com.example.textrpg.Constants.First_Visit_Main_Information;
import static com.example.textrpg.Constants.First_Visit_Skills;
import static com.example.textrpg.Constants.First_Visit_Talents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.textrpg.R;
import com.example.textrpg.StartingFragments.StartingInformationFragment;
import com.example.textrpg.databinding.ActivityCreatingCharacterBinding;

public class CreatingCharacter extends AppCompatActivity {
    private ActivityCreatingCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingCharacterBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        getPreferences(MODE_PRIVATE).edit().putBoolean(First_Visit_Characteristics, true).
                putBoolean(First_Visit_Skills, true).
                putBoolean(First_Visit_Main_Information, true).
                putBoolean(First_Visit_Talents, true).
                apply();
        getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
    }

}