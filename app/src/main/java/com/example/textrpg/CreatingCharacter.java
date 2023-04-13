package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.textrpg.StartingFragments.StartingInformationFragment;
import com.example.textrpg.databinding.ActivityCreatingCharacterBinding;

public class CreatingCharacter extends AppCompatActivity {
    private ActivityCreatingCharacterBinding binding;
    private final String First_Visit_Characteristics = "First Visit Characteristics",
            First_Visit_Skills = "First Visit Skills",
            First_Visit_Main_Information = "First Visit Main Information",
            First_Visit_Talents = "First Visit Talents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getPreferences(MODE_PRIVATE).edit().putBoolean(First_Visit_Characteristics, true).
                putBoolean(First_Visit_Skills, true).
                putBoolean(First_Visit_Main_Information, true).
                putBoolean(First_Visit_Talents, true).
                apply();
        getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
    }
}