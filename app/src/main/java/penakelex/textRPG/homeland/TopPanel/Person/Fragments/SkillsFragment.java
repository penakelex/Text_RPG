package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Skill_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import penakelex.textRPG.homeland.Adapters.Skills.SkillAdapter;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentSkillsBinding;

public class SkillsFragment extends Fragment {
    private FragmentSkillsBinding binding;
    private SkillAdapter skillAdapter;
    private SharedPreferences sharedPreferences;
    private SkillTableHelper skillTableHelper;
    private final SkillAdapter.OnSkillItemClickListener clickListener = new SkillAdapter.OnSkillItemClickListener() {
        @Override
        public void onClickListener(SkillsItem skill) {
            binding.skillName.setText(requireActivity().getResources().getString(skill.getName()));
            switch (skill.getID()) {
                case 1 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.light_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.light_weapons_base_value));
                }
                case 2 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.heavy_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.heavy_weapons_base_value));
                }
                case 3 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.melee_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.melee_weapons_base_value));
                }
                case 4 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.communication_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.communication_base_value));
                }
                case 5 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.trading_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.trading_base_value));
                }
                case 6 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.survival_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.survival_base_value));
                }
                case 7 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.medicine_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.medicine_base_value));
                }
                case 8 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.science_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.science_base_value));
                }
                case 9 -> {
                    binding.skillDescription.setText(getResources().getString(R.string.repair_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.repair_base_value));
                }
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void setNewPoints() {
            if (sharedPreferences == null) {
                sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            }
            binding.skillsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Skill_Points, 0)));
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        skillTableHelper = new SkillTableHelper(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickListener.setNewPoints();
        skillAdapter = new SkillAdapter(clickListener, binding.getRoot());
        settingAdapterInformation();
        binding.containerForSkills.setAdapter(skillAdapter);
        binding.saveChanges.setOnClickListener(listener -> savingChanges());
    }

    private void settingAdapterInformation() {
        skillTableHelper.setAdapterInformation(skillAdapter, requireActivity());
    }

    private void savingChanges() {
        skillTableHelper.saveChanges(skillAdapter.getInformation(), binding.getRoot(), requireActivity());
    }
}