package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.StartingTalents.StartingTalentsAdapter;

import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentsTableHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.ViewModels.TalentsViewModel.TalentsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingTalentsBinding;

public class StartingTalentsFragment extends Fragment {
    private FragmentStartingTalentsBinding binding;
    private SharedPreferences sharedPreferences;
    private StartingTalentsAdapter startingTalentsAdapter;
    private TalentsTableHelper tableHelper;
    private TalentsViewModel talentsViewModel;
    private byte ID = -1;
    private final StartingTalentsAdapter.OnStartingTalentItemClickListener clickListener = new StartingTalentsAdapter.OnStartingTalentItemClickListener() {
        @Override
        public void onClickListener(TalentItem talent) {
            binding.talentName.setText(requireActivity().getResources().getString(talent.getName()));
            ID = talent.getID();
            switch (ID) {
                case 1 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_singer));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_singer));
                }
                case 2 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_bull));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_bull));
                }
                case 3 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_strong_kick));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_strong_kick));
                }
                case 4 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_experienced));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_experienced));
                }
                case 5 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_trained));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_trained));
                }
                case 6 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_heavyweight));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_heavyweight));
                }
                case 7 -> {
                    binding.talentDescription.setText(getResources().getString(R.string.description_kind_one));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_kind_one));
                }
            }
            if (talent.isHaving()) {
                binding.chooseTalent.setText(R.string.unchoose_talent);
            } else {
                binding.chooseTalent.setText(R.string.choose_talent);
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartingTalentsBinding.inflate(inflater, container, false);
        startingTalentsAdapter = new StartingTalentsAdapter(clickListener);
        tableHelper();
        return binding.getRoot();
    }

    private void tableHelper() {
        talentsViewModel = new ViewModelProvider(requireActivity()).get(TalentsViewModel.class);
        talentsViewModel.initiate(requireActivity().getApplication());
        CharacteristicsViewModel characteristicsViewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(requireActivity().getApplication());
        SkillsViewModel skillsViewModel = new ViewModelProvider(requireActivity()).get(SkillsViewModel.class);
        skillsViewModel.initiate(requireActivity().getApplication());
        OtherInformationViewModel otherInformationViewModel = new ViewModelProvider(requireActivity()).get(OtherInformationViewModel.class);
        otherInformationViewModel.initiate(requireActivity().getApplication());
        tableHelper = new TalentsTableHelper(characteristicsViewModel, skillsViewModel, talentsViewModel, otherInformationViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNewInformation();
        binding.containerForSTalents.setAdapter(startingTalentsAdapter);
        buttons();
    }

    private void buttons() {
        binding.chooseTalent.setOnClickListener(listener -> choosingTalent());
        binding.buttonToSkills.setOnClickListener(listener -> backToSkills());
        binding.buttonToEndForm.setOnClickListener(listener -> endForm());
    }

    @SuppressLint("DefaultLocale")
    private void setPointsInformation() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        binding.talentsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_talents_points), sharedPreferences.getInt(Talents_Points, 2)));
    }

    private void choosingTalent() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        tableHelper.choosingTalent(sharedPreferences, ID, binding, requireActivity());
        setNewInformation();
    }

    private void setNewInformation() {
        startingTalentsAdapter.setInformation(talentsViewModel.getAllTalents(), requireActivity());
        setPointsInformation();
        updateButtonTitle();
    }

    private void updateButtonTitle() {
        if (ID != -1) {
            binding.chooseTalent.setText(!talentsViewModel.getTalent(ID).isHaving() ? requireActivity().getResources().getString(R.string.choose_talent) : requireActivity().getResources().getString(R.string.unchoose_talent));
        }
    }

    private void endForm() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        if (sharedPreferences.getInt(Talents_Points, 2) == 0) {
            sharedPreferences.edit().putBoolean(First_Visit_Talents, false).apply();
            new EndingForm().show(requireActivity().getFragmentManager(), "ending form");
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_ur_talents), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void backToSkills() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        if (sharedPreferences.getInt(Talents_Points, 2) == 0) {
            sharedPreferences.edit().putBoolean(First_Visit_Talents, false).apply();
            binding = null;
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_ur_talents), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
}