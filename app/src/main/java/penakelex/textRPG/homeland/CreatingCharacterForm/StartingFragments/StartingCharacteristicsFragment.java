package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.StartingCharacteristics.StartingCharacteristicsAdapter;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingCharacteristicsBinding;

public class StartingCharacteristicsFragment extends Fragment {
    private FragmentStartingCharacteristicsBinding binding;
    private int currentPosition = -1;
    private SharedPreferences sharedPreferences;

    private StartingCharacteristicsAdapter startingCharacteristicsAdapter;
    private final StartingCharacteristicsAdapter.OnStartingCharacteristicItemClickListener listener = new StartingCharacteristicsAdapter.OnStartingCharacteristicItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.characteristicName.setText(name);
            currentPosition = position;
            switch (position) {
                case 0:
                    binding.characteristicDescription.setText(getResources().getString(R.string.strength_description));
                    break;
                case 1:
                    binding.characteristicDescription.setText(getResources().getString(R.string.physique_description));
                    break;
                case 2:
                    binding.characteristicDescription.setText(getResources().getString(R.string.dexterity_description));
                    break;
                case 3:
                    binding.characteristicDescription.setText(getResources().getString(R.string.mentality_description));
                    break;
                case 4:
                    binding.characteristicDescription.setText(getResources().getString(R.string.luckiness_description));
                    break;
                case 5:
                    binding.characteristicDescription.setText(getResources().getString(R.string.watchfulness_description));
                    break;
                case 6:
                    binding.characteristicDescription.setText(getResources().getString(R.string.attractiveness_description));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingCharacteristicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startingCharacteristicsAdapter = new StartingCharacteristicsAdapter(listener);
        startingCharacteristicsAdapter.setInformation(getActivity());
        setNewPoints();
        binding.containerForSCharacteristics.setAdapter(startingCharacteristicsAdapter);
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        SQLiteDatabase characteristicsDatabase = new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase();
        binding.downgrade.setOnClickListener(l -> downgradingCharacteristic(characteristicsDatabase));
        binding.raise.setOnClickListener(l -> raisingCharacteristic(characteristicsDatabase));
        binding.buttonToSkills.setOnClickListener(l -> settingSkillsFragment());
        binding.buttonToMainInfo.setOnClickListener(l -> settingMainInfoFragment());
    }

    private void raisingCharacteristic(SQLiteDatabase characteristicsDatabase) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int result = CharacteristicsDatabase.raiseCharacteristic(characteristicsDatabase, sharedPreferences, currentPosition + 1);
        switch (result) {
            case 0:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_characteristic), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 1:
                sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) - 1).apply();setNewPoints();
                break;
            case 2:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.max_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 3:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.not_enough_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
        }
        setNewInformation();
    }

    private void downgradingCharacteristic(SQLiteDatabase characteristicsDatabase) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int result = CharacteristicsDatabase.downgradeCharacteristic(characteristicsDatabase, currentPosition + 1);
        switch (result) {
            case 1:
                sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) + 1).apply();
                setNewPoints();
                break;
            case 2:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.min_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 3:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_characteristic), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
        }
        setNewInformation();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setNewInformation() {
        startingCharacteristicsAdapter.setInformation(getActivity());
        startingCharacteristicsAdapter.notifyDataSetChanged();
    }

    private void settingSkillsFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_cant_go_while_not_spend_all_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        } else {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        }
    }

    private void settingMainInfoFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_cant_go_while_not_spend_all_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        } else {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }

    @SuppressLint("DefaultLocale")
    private void setNewPoints() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        binding.characteristicsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_characteristics_points), sharedPreferences.getInt(Characteristics_Points, 2)));
    }
}