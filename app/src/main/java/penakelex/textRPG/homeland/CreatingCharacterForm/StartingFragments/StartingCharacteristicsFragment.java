package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

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

import penakelex.textRPG.homeland.Adapters.StartingCharacteristics.StartingCharacteristicsAdapter;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicsTableHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingCharacteristicsBinding;

public class StartingCharacteristicsFragment extends Fragment {
    private FragmentStartingCharacteristicsBinding binding;
    private byte ID = -1;
    private SharedPreferences sharedPreferences;
    private CharacteristicsViewModel viewModel;
    private StartingCharacteristicsAdapter startingCharacteristicsAdapter;
    private CharacteristicsTableHelper tableHelper;
    private final StartingCharacteristicsAdapter.OnStartingCharacteristicItemClickListener listener = new StartingCharacteristicsAdapter.OnStartingCharacteristicItemClickListener() {
        @Override
        public void onClickListener(CharacteristicItem item) {
            binding.characteristicName.setText(requireActivity().getResources().getString(item.getName()));
            ID = item.getID();
            switch (ID) {
                case 1 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.strength_description));
                case 2 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.physique_description));
                case 3 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.dexterity_description));
                case 4 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.mentality_description));
                case 5 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.luckiness_description));
                case 6 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.watchfulness_description));
                case 7 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.attractiveness_description));
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartingCharacteristicsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        viewModel.initiate(requireActivity().getApplication());
        tableHelper = new CharacteristicsTableHelper(viewModel);
        startingCharacteristicsAdapter = new StartingCharacteristicsAdapter(listener);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingInformation();
        binding.containerForSCharacteristics.setAdapter(startingCharacteristicsAdapter);
        buttons();
    }

    private void settingInformation() {
        startingCharacteristicsAdapter.setInformation(viewModel.getAllCharacteristics(), requireActivity());
        setNewPoints();
    }

    private void buttons() {
        binding.raise.setOnClickListener(listener -> raisingCharacteristic());
        binding.downgrade.setOnClickListener(listener -> downgradingCharacteristic());
        binding.buttonToMainInfo.setOnClickListener(listener -> settingMainInfoFragment());
        binding.buttonToSkills.setOnClickListener(listener -> settingSkillsFragment());
    }

    private void raisingCharacteristic() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        tableHelper.raiseCharacteristic(sharedPreferences, ID, binding, requireActivity());
        settingInformation();
    }

    private void downgradingCharacteristic() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        tableHelper.downgradeCharacteristic(sharedPreferences, ID, binding, requireActivity());
        settingInformation();
    }

    private void settingSkillsFragment() {
        if (requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_cant_go_while_not_spend_all_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        } else {
            binding = null;
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        }
    }

    private void settingMainInfoFragment() {
        if (requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_cant_go_while_not_spend_all_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        } else {
            binding = null;
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }

    @SuppressLint("DefaultLocale")
    private void setNewPoints() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        binding.characteristicsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_characteristics_points), sharedPreferences.getInt(Characteristics_Points, 2)));
    }
}