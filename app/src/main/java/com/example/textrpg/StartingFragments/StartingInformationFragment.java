package com.example.textrpg.StartingFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingInformationBinding;


public class StartingInformationFragment extends Fragment {
    private FragmentStartingInformationBinding binding;
    SharedPreferences sharedPreferences;
    private final String Main_Character_Name = "Main Character Name",
            Main_Character_Age = "Main Character Age", Main_Character_Height = "Main Character Height",
            Gender = "Gender", First_Visit_Main_Information = "First Visit Main Information";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingStartValues();
        binding.nextToCharacteristics.setOnClickListener(listener -> savingData());
    }

    private void settingStartValues() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(First_Visit_Main_Information, true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Main_Character_Age, 0).apply();
            editor.putInt(Main_Character_Height, 0).apply();
            editor.putString(Main_Character_Name, "").apply();
            editor.putInt(Gender, 0).apply();
            editor.putBoolean(First_Visit_Main_Information, false).apply();
        }
        if (!sharedPreferences.getString(Main_Character_Name, "").equals("")) {
            binding.characterName.setText(sharedPreferences.getString(Main_Character_Name, ""));
        } else {
            binding.characterName.setText("");
            binding.characterName.setHint("Введите Ваше имя");
        }
        if (sharedPreferences.getInt(Main_Character_Height, 0) != 0) {
            binding.characterHeight.setText(String.valueOf(sharedPreferences.getInt(Main_Character_Height, 0)));
        } else {
            binding.characterHeight.setText("");
            binding.characterHeight.setHint("Введите Ваш рост");
        }
        if (sharedPreferences.getInt(Main_Character_Age, 0) != 0) {
            binding.characterAge.setText(String.valueOf(sharedPreferences.getInt(Main_Character_Age, 0)));
        } else {
            binding.characterAge.setText("");
            binding.characterAge.setHint("Введите Ваш возраст");
        }
        if (sharedPreferences.getInt(Gender, 0) != 0) {
            int gender = sharedPreferences.getInt(Gender, 0);
            switch (gender) {
                case 1:
                    binding.male.setChecked(true);
                    break;
                case 2:
                    binding.female.setChecked(true);
                    break;
            }
        }
    }

    private void savingData() {
        binding.message.setText("");
        boolean bool = true;
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (binding.characterName.getText().toString().equals("")) {
            binding.message.setText("Вы не заполнили Ваше имя...\n");
            bool = false;
        } else {
            sharedPreferences.edit().putString(Main_Character_Name, binding.characterName.getText().toString()).apply();
        }
        if (binding.characterAge.getText().toString().equals("")) {
            binding.message.setText(String.format("%sВы не заполнили свой возраст...\n", binding.message.getText().toString()));
            bool = false;
        } else {
            if (binding.characterAge.getText().toString().split("").length <= 4){
                if (Integer.parseInt(binding.characterAge.getText().toString()) > 35) {
                    binding.message.setText(String.format("%sВы слишком *взрослый(-ая)* для космоса...\n", binding.message.getText().toString()));
                    bool = false;
                } else if (Integer.parseInt(binding.characterAge.getText().toString()) < 16) {
                    binding.message.setText(String.format("%sВы слишком *молодой(-ая)* для космоса...\n", binding.message.getText().toString()));
                    bool = false;
                } else {
                    sharedPreferences.edit().putInt(Main_Character_Age, Integer.parseInt(binding.characterAge.getText().toString())).apply();
                }
            } else {
                binding.message.setText(String.format("%sВы чрезмерно *взрослый(-ая)* для космоса...\n", binding.message.getText().toString()));
                bool = false;
            }
        }
        if (binding.characterHeight.getText().toString().equals("")) {
            binding.message.setText(String.format("%sВы не указали свой рост...\n", binding.message.getText().toString()));
            bool = false;
        } else {
            if (binding.characterHeight.getText().toString().split("").length <= 4) {
                if (Integer.parseInt(binding.characterHeight.getText().toString()) > 190) {
                    binding.message.setText(String.format("%sВаш рост слишком большой...\n", binding.message.getText().toString()));
                    bool = false;
                } else if (Integer.parseInt(binding.characterHeight.getText().toString()) < 150) {
                    binding.message.setText(String.format("%sВаш рост слишком маленький...\n", binding.message.getText().toString()));
                    bool = false;
                } else {
                    sharedPreferences.edit().putInt(Main_Character_Height, Integer.parseInt(binding.characterHeight.getText().toString())).apply();
                }
            } else {
                binding.message.setText(String.format("%sВаш рост чрезмерно большой...\n", binding.message.getText().toString()));
                bool = false;
            }
        }
        if (binding.selectingSex.getCheckedRadioButtonId() == -1) {
            binding.message.setText(String.format("%sВы не выбрали Ваш пол...\n", binding.message.getText().toString()));
            bool = false;
        } else {
            if (binding.male.isChecked()) {
                sharedPreferences.edit().putInt(Gender, 1).apply();
            } else if (binding.female.isChecked()) {
                sharedPreferences.edit().putInt(Gender, 2).apply();
            }
        }
        if (bool) {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        }
    }
}