package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Age;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Height;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingInformationBinding;


public class StartingInformationFragment extends Fragment {
    private FragmentStartingInformationBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartingInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingStartValues();
        binding.nextToCharacteristics.setOnClickListener(listener -> savingData());
    }

    private void settingStartValues() {
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
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
            binding.characterAge.setValue(sharedPreferences.getInt(Main_Character_Age, 0));
        } else {
            binding.characterAge.setValue(16);
        }
        binding.characterAge.setMinValue(16);
        binding.characterAge.setMaxValue(35);
        if (sharedPreferences.getInt(Gender, 0) != 0) {
            switch (sharedPreferences.getInt(Gender, 0)) {
                case 1 -> binding.male.setChecked(true);
                case 2 -> binding.female.setChecked(true);
            }
        }
    }

    private void savingData() {
        boolean isAllGood = true;
        if (sharedPreferences == null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        if (binding.characterName.getText().toString().equals("")) {
            binding.characterName.setHint(getResources().getString(R.string.did_not_input_ur_name));
            binding.characterName.setHintTextColor(getResources().getColor(R.color.red));
            isAllGood = false;
        } else {
            String name = binding.characterName.getText().toString().replace("\n", "").replace(" ", "");
            sharedPreferences.edit().putString(Main_Character_Name, name).apply();
        }
        sharedPreferences.edit().putInt(Main_Character_Age, binding.characterAge.getValue()).apply();
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
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        }
    }
}