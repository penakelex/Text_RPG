package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Age;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Height;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingInformationBinding;


public class StartingInformationFragment extends Fragment {
    private FragmentStartingInformationBinding binding;
    SharedPreferences sharedPreferences;

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
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(Main_Character_Name, "").equals("")) {
            binding.characterName.setText(sharedPreferences.getString(Main_Character_Name, ""));
        } else {
            binding.characterName.setText("");
            binding.characterName.setHint(getResources().getString(R.string.input_name));
        }
        if (sharedPreferences.getInt(Main_Character_Height, 0) != 0) {
            binding.characterHeight.setText(String.valueOf(sharedPreferences.getInt(Main_Character_Height, 0)));
        } else {
            binding.characterHeight.setText("");
            binding.characterHeight.setHint(getResources().getString(R.string.input_height));
        }
        if (sharedPreferences.getInt(Main_Character_Age, 0) != 0) {
            binding.characterAge.setText(String.valueOf(sharedPreferences.getInt(Main_Character_Age, 0)));
        } else {
            binding.characterAge.setText("");
            binding.characterAge.setHint(R.string.input_age);
        }
        if (sharedPreferences.getInt(Gender, 0) != 0) {
            switch (sharedPreferences.getInt(Gender, 0)) {
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
        boolean isAllGood = true;
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (binding.characterName.getText().toString().equals("")) {
            binding.characterName.setHint(getResources().getString(R.string.did_not_input_ur_name));
            binding.characterName.setHintTextColor(getResources().getColor(R.color.red));
            isAllGood = false;
            binding.characterAge.setText("");
        } else {
            sharedPreferences.edit().putString(Main_Character_Name, binding.characterName.getText().toString()).apply();
        }
        if (binding.characterAge.getText().toString().equals("")) {
            binding.characterAge.setHint(getResources().getString(R.string.did_not_input_ur_age));
            binding.characterAge.setHintTextColor(getResources().getColor(R.color.red));
            isAllGood = false;
            binding.characterAge.setText("");
        } else {
            if (binding.characterAge.getText().toString().split("").length <= 4) {
                if (Integer.parseInt(binding.characterAge.getText().toString()) > 35) {
                    binding.characterAge.setHint(getResources().getString(R.string.ur_too_old));
                    binding.characterAge.setHintTextColor(getResources().getColor(R.color.red));
                    isAllGood = false;
                    binding.characterAge.setText("");
                } else if (Integer.parseInt(binding.characterAge.getText().toString()) < 16) {
                    binding.characterAge.setHint(getResources().getString(R.string.ur_not_enough_old));
                    binding.characterAge.setHintTextColor(getResources().getColor(R.color.red));
                    isAllGood = false;
                    binding.characterAge.setText("");
                } else {
                    sharedPreferences.edit().putInt(Main_Character_Age, Integer.parseInt(binding.characterAge.getText().toString())).apply();
                }
            } else {
                binding.characterAge.setHintTextColor(getResources().getColor(R.color.red));
                binding.characterAge.setHint(getResources().getString(R.string.ur_sooo_old));
                isAllGood = false;
                binding.characterAge.setText("");
            }
        }
        if (binding.characterHeight.getText().toString().equals("")) {
            binding.characterHeight.setHint(getResources().getString(R.string.did_not_input_ur_height));
            binding.characterHeight.setHintTextColor(getResources().getColor(R.color.red));
            isAllGood = false;
        } else {
            if (binding.characterHeight.getText().toString().split("").length <= 4) {
                if (Integer.parseInt(binding.characterHeight.getText().toString()) > 190) {
                    binding.characterHeight.setHint(R.string.ur_too_tall);
                    binding.characterHeight.setHintTextColor(getResources().getColor(R.color.red));
                    isAllGood = false;
                    binding.characterHeight.setText("");
                } else if (Integer.parseInt(binding.characterHeight.getText().toString()) < 150) {
                    binding.characterHeight.setHint(R.string.ur_too_short);
                    binding.characterHeight.setHintTextColor(getResources().getColor(R.color.red));
                    isAllGood = false;
                    binding.characterHeight.setText("");
                } else {
                    sharedPreferences.edit().putInt(Main_Character_Height, Integer.parseInt(binding.characterHeight.getText().toString())).apply();
                }
            } else {
                binding.characterHeight.setHint(R.string.ur_sooo_tall);
                binding.characterHeight.setHintTextColor(getResources().getColor(R.color.red));
                isAllGood = false;
                binding.characterHeight.setText("");
            }
        }
        if (binding.selectingGender.getCheckedRadioButtonId() == -1) {
            binding.gender.setTextColor(getResources().getColor(R.color.red));
            isAllGood = false;
        } else {
            if (binding.male.isChecked()) {
                sharedPreferences.edit().putInt(Gender, 1).apply();
            } else if (binding.female.isChecked()) {
                sharedPreferences.edit().putInt(Gender, 2).apply();
            }
        }
        if (isAllGood) {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        }
    }
}