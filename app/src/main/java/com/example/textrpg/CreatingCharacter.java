package com.example.textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.textrpg.StartingFragments.StartingInformationFragment;
import com.example.textrpg.databinding.ActivityCreatingCharacterBinding;

public class CreatingCharacter extends AppCompatActivity {
    private ActivityCreatingCharacterBinding binding;
    private final String Starting_To_Creating_Character = "Starting To Creating Character";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getPreferences(MODE_PRIVATE).edit().putBoolean(Starting_To_Creating_Character, true).apply();
        getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
    }
}