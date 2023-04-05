package com.example.textrpg.StartingFragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingSkillsBinding;

public class StartingSkillsFragment extends Fragment {
    private FragmentStartingSkillsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.lightWeapons.setOnClickListener(listener -> settingLightWeaponsInformation());
        binding.heavyWeapons.setOnClickListener(listener -> settingHeavyWeaponsInformation());
        binding.meleeWeapons.setOnClickListener(listener -> settingMeleeWeaponsInformation());
        binding.communication.setOnClickListener(listener -> settingCommunicationInformation());
        binding.trading.setOnClickListener(listener -> settingTradingInformation());
        binding.survival.setOnClickListener(listener -> settingSurvivalInformation());
        binding.medicine.setOnClickListener(listener -> settingMedicineInformation());
        binding.scince.setOnClickListener(listener -> settingScienceInformation());
        binding.repair.setOnClickListener(listener -> settingRepairInformation());
        binding.chooseAsMainSkill.setOnClickListener(listener -> choosingAsMainSkill());
    }

    private void choosingAsMainSkill() {
        switch (binding.skillName.getText().toString()) {
            case "Лёгкое оружие":
            case "Тяжёлое оружие":
            case "Оружие ближнего боя":
            case "Общение":
            case "Торговля":
            case "Выживание":
            case "Медицина":
            case "Наука":
            case "Ремонт":
        }

    }

    private void settingRepairInformation() {
        binding.skillName.setText("Ремонт");
        binding.descriptionSkills.setText("");
    }

    private void settingScienceInformation() {
        binding.skillName.setText("Наука");
        binding.descriptionSkills.setText("");
    }

    private void settingMedicineInformation() {
        binding.skillName.setText("Медицина");
        binding.descriptionSkills.setText("");
    }

    private void settingSurvivalInformation() {
        binding.skillName.setText("Выживание");
        binding.descriptionSkills.setText("");
    }

    private void settingTradingInformation() {
        binding.skillName.setText("Торговля");
        binding.descriptionSkills.setText("");
    }

    private void settingCommunicationInformation() {
        binding.skillName.setText("Общение");
        binding.descriptionSkills.setText("");
    }

    private void settingMeleeWeaponsInformation() {
        binding.skillName.setText("Оружие ближнего боя");
        binding.descriptionSkills.setText("");
    }

    private void settingHeavyWeaponsInformation() {
        binding.skillName.setText("Тяжёлое оружие");
        binding.descriptionSkills.setText("");
    }

    private void settingLightWeaponsInformation() {
        binding.skillName.setText("Лёгкое оружие");
        binding.descriptionSkills.setText("Отображает Ваше умение пользоваться лёгким оружием: пистолетами, пистолетами-пулемётами, винтовками.");
    }
}