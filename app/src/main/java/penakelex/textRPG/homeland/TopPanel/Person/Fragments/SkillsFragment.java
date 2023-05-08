package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Skill_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Adapters.Skills.SkillAdapter;
import penakelex.textRPG.homeland.Adapters.Skills.SkillInformation;
import penakelex.textRPG.homeland.Adapters.StartingSkills.StartingSkillsAdapter;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentSkillsBinding;

public class SkillsFragment extends android.app.Fragment {
    private FragmentSkillsBinding binding;
    private SkillAdapter skillAdapter;
    private SharedPreferences sharedPreferences;
    private SkillAdapter.OnSkillItemClickListener clickListener = new SkillAdapter.OnSkillItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.skillName.setText(name);
            switch (position) {
                case 0:
                    binding.skillDescription.setText(getResources().getString(R.string.light_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.light_weapons_base_value));
                    break;
                case 1:
                    binding.skillDescription.setText(getResources().getString(R.string.heavy_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.heavy_weapons_base_value));
                    break;

                case 2:
                    binding.skillDescription.setText(getResources().getString(R.string.melee_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.melee_weapons_base_value));
                    break;

                case 3:
                    binding.skillDescription.setText(getResources().getString(R.string.communication_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.communication_base_value));
                    break;

                case 4:
                    binding.skillDescription.setText(getResources().getString(R.string.trading_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.trading_base_value));
                    break;

                case 5:
                    binding.skillDescription.setText(getResources().getString(R.string.survival_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.survival_base_value));
                    break;

                case 6:
                    binding.skillDescription.setText(getResources().getString(R.string.medicine_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.medicine_base_value));
                    break;

                case 7:
                    binding.skillDescription.setText(getResources().getString(R.string.science_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.science_base_value));
                    break;

                case 8:
                    binding.skillDescription.setText(getResources().getString(R.string.repair_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.repair_base_value));
                    break;
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void setNewPoints() {
            if (sharedPreferences == null) {
                sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            }
            binding.skillsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Skill_Points, 0)));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickListener.setNewPoints();
        skillAdapter = new SkillAdapter(clickListener);
        skillAdapter.setInformation(getActivity(), binding.getRoot());
        binding.containerForSkills.setAdapter(skillAdapter);
        binding.saveChanges.setOnClickListener(listener -> savingChanges());
    }

    private void savingChanges() {
        ArrayList<SkillInformation> skillInformations = skillAdapter.getInformation();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (SkillInformation skillInformation : skillInformations) {
            arrayList.add(skillInformation.getValue());
        }
        SkillsDatabase.updateSkillsValues(new SkillsDatabaseHelper(getActivity()).getWritableDatabase(), arrayList);
        Snackbar.make(binding.getRoot(), getResources().getString(R.string.changes_saved), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
    }
}