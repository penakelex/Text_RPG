package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.StartingSkills.StartingSkillsAdapter;


import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.Main.Constants;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingSkillsBinding;


public class StartingSkillsFragment extends Fragment {
    private FragmentStartingSkillsBinding binding;
    private StartingSkillsAdapter startingSkillsAdapter;
    private byte ID = -1;
    private SkillsViewModel skillsViewModel;
    private SharedPreferences sharedPreferences;
    private SkillTableHelper tableHelper;
    private LiveData<List<SkillsItem>> skills;
    private final StartingSkillsAdapter.OnSkillItemClickListener clickListener = new StartingSkillsAdapter.OnSkillItemClickListener() {
        @Override
        public void onClickListener(SkillsItem item) {
            binding.skillName.setText(requireActivity().getResources().getString(item.getName()));
            ID = item.getID();
            switch (item.getID()) {
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
            if (item.isMain()) {
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_not_main_skill));
            } else {
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_main_skill));
            }
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartingSkillsBinding.inflate(inflater, container, false);
        CharacteristicsViewModel characteristicsViewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(requireActivity().getApplication());
        skillsViewModel = new ViewModelProvider(requireActivity()).get(SkillsViewModel.class);
        skillsViewModel.initiate(requireActivity().getApplication());
        tableHelper = new SkillTableHelper(characteristicsViewModel, skillsViewModel, requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableHelper.updateSkillValues();
        startingSkillsAdapter = new StartingSkillsAdapter(clickListener);
        binding.containerForSSkills.setAdapter(startingSkillsAdapter);
        skills = skillsViewModel.getAllSkills();
        setNewInformation();
        setPointsInformation();
        buttons();
    }

    private void setNewInformation() {
        skills.observe(requireActivity(), skillsItems -> startingSkillsAdapter.setInformation(skillsItems, requireActivity()));
    }

    private void buttons() {
        binding.chooseAsMainSkill.setOnClickListener(listener -> choosingSkill());
        binding.buttonToCharacteristics.setOnClickListener(listener -> settingCharacteristicsFragment());
        binding.buttonToTalents.setOnClickListener(listener -> settingAbilitiesFragment());
    }

    private void choosingSkill() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        tableHelper.choosingSkill(sharedPreferences, ID, binding, requireActivity(), skills);
        setNewInformation();
    }

    @SuppressLint("DefaultLocale")
    private void setPointsInformation() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        binding.skillsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Main_Skills, 3)));
    }

    private void settingCharacteristicsFragment() {
        if (requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            tableHelper.updateSkillValues();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_used_all_main_skills_points_yet), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingAbilitiesFragment() {
        if (requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            tableHelper.updateSkillValues();
            new OtherInformationTableHelper(requireActivity()).updateInformation();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingTalentsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_used_all_main_skills_points_yet), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
}