package com.example.textrpg.StartingFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingCharacteristicsBinding;

public class StartingCharacteristicsFragment extends Fragment {
    private FragmentStartingCharacteristicsBinding binding;
    SharedPreferences sharedPreferences;
    private final String Characteristic = "Characteristic", Characteristics_Points = "Characteristics Points",
            Current_Characteristic = "Current Characteristic", Strength = "Strength", Physique = "Physique",
            Dexterity = "Dexterity", Mentality = "Mentality", Luckiness = "Luckiness", Watchfulness = "Watchfulness",
            Attractiveness = "Attractiveness", Starting_To_Creating_Character = "Starting To Creating Character";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingCharacteristicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingDefaultValues();
        binding.strength.setOnClickListener(listener -> settingStrengthInformation());
        binding.physique.setOnClickListener(listener -> settingPhysiqueInformation());
        binding.dexterity.setOnClickListener(listener -> settingDexterityInformation());
        binding.mentality.setOnClickListener(listener -> settingMentalityInformation());
        binding.luckiness.setOnClickListener(listener -> settingLuckinessInformation());
        binding.watchfulness.setOnClickListener(listener -> settingWatchfulnessInformation());
        binding.attractiveness.setOnClickListener(listener -> settingAttractivenessInformation());
        binding.downgrade.setOnClickListener(listener -> downgrading());
        binding.raise.setOnClickListener(listener -> raising());
        binding.buttonToMainInfo.setOnClickListener(listener -> settingMainInfoFragment());
        binding.buttonToSkills.setOnClickListener(listener -> settingSkillsFragment());
    }

    private void settingSkillsFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Characteristics_Points, 16) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        }
    }

    private void settingMainInfoFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Characteristics_Points, 16) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }

    @SuppressLint("DefaultLocale")
    private void settingDefaultValues() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Starting_To_Creating_Character, true)) {
            sharedPreferences.edit().putInt(Strength, 0).putInt(Physique, 0).putInt(Dexterity, 0).putInt(Mentality, 0).
                    putInt(Luckiness, 0).putInt(Watchfulness, 0).putInt(Attractiveness, 0).
                    putInt(Characteristics_Points, 16).putBoolean(Starting_To_Creating_Character, false).
                    apply();
        }
        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                sharedPreferences.getInt(Characteristics_Points, 16)));
        binding.strengthValue.setText(String.valueOf(sharedPreferences.getInt(Strength, 0)));
        binding.physiqueValue.setText(String.valueOf(sharedPreferences.getInt(Physique, 0)));
        binding.dexterityValue.setText(String.valueOf(sharedPreferences.getInt(Dexterity, 0)));
        binding.mentalityValue.setText(String.valueOf(sharedPreferences.getInt(Mentality, 0)));
        binding.luckinessValue.setText(String.valueOf(sharedPreferences.getInt(Luckiness, 0)));
        binding.watchfulnessValue.setText(String.valueOf(sharedPreferences.getInt(Watchfulness, 0)));
        binding.attractivenessValue.setText(String.valueOf(sharedPreferences.getInt(Attractiveness, 0)));
    }

    @SuppressLint("DefaultLocale")
    private void raising() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        binding.message.setText("");
        if (binding.descriptionCharacteristics.getText().toString().equals("")) {
            binding.message.setText("Вы не выбрали характеристику...");
        } else {
            switch (sharedPreferences.getInt(Current_Characteristic, 0)) {
                case 1:
                    if (Integer.parseInt(binding.strengthValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.strengthValue.setText(String.valueOf(Integer.parseInt(binding.strengthValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Strength, Integer.parseInt(binding.strengthValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 2:
                    if (Integer.parseInt(binding.physiqueValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.physiqueValue.setText(String.valueOf(Integer.parseInt(binding.physiqueValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Physique, Integer.parseInt(binding.physiqueValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 3:
                    if (Integer.parseInt(binding.dexterityValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.dexterityValue.setText(String.valueOf(Integer.parseInt(binding.dexterityValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Dexterity, Integer.parseInt(binding.dexterityValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 4:
                    if (Integer.parseInt(binding.mentalityValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.mentalityValue.setText(String.valueOf(Integer.parseInt(binding.mentalityValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Mentality, Integer.parseInt(binding.mentalityValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 5:
                    if (Integer.parseInt(binding.luckinessValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.luckinessValue.setText(String.valueOf(Integer.parseInt(binding.luckinessValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Luckiness, Integer.parseInt(binding.luckinessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 6:
                    if (Integer.parseInt(binding.watchfulnessValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.watchfulnessValue.setText(String.valueOf(Integer.parseInt(binding.watchfulnessValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Watchfulness, Integer.parseInt(binding.watchfulnessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
                case 7:
                    if (Integer.parseInt(binding.attractivenessValue.getText().toString()) + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 16) - 1 >= 0) {
                        binding.attractivenessValue.setText(String.valueOf(Integer.parseInt(binding.attractivenessValue.getText().toString()) + 1));
                        sharedPreferences.edit().putInt(Attractiveness, Integer.parseInt(binding.attractivenessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) - 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком большое значение характеристики...");
                    }
                    break;
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void downgrading() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        binding.message.setText("");
        if (binding.descriptionCharacteristics.getText().toString().equals("")) {
            binding.message.setText("Вы не выбрали характеристику...");
        } else {
            switch (sharedPreferences.getInt(Current_Characteristic, 0)) {
                case 1:
                    if (Integer.parseInt(binding.strengthValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.strengthValue.setText(String.valueOf(Integer.parseInt(binding.strengthValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Strength, Integer.parseInt(binding.strengthValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 2:
                    if (Integer.parseInt(binding.physiqueValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.physiqueValue.setText(String.valueOf(Integer.parseInt(binding.physiqueValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Physique, Integer.parseInt(binding.physiqueValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 3:
                    if (Integer.parseInt(binding.dexterityValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.dexterityValue.setText(String.valueOf(Integer.parseInt(binding.dexterityValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Dexterity, Integer.parseInt(binding.dexterityValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 4:
                    if (Integer.parseInt(binding.mentalityValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.mentalityValue.setText(String.valueOf(Integer.parseInt(binding.mentalityValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Mentality, Integer.parseInt(binding.mentalityValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 5:
                    if (Integer.parseInt(binding.luckinessValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.luckinessValue.setText(String.valueOf(Integer.parseInt(binding.luckinessValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Luckiness, Integer.parseInt(binding.luckinessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 6:
                    if (Integer.parseInt(binding.watchfulnessValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.watchfulnessValue.setText(String.valueOf(Integer.parseInt(binding.watchfulnessValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Watchfulness, Integer.parseInt(binding.watchfulnessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
                case 7:
                    if (Integer.parseInt(binding.attractivenessValue.getText().toString()) - 1 >= 0 && sharedPreferences.getInt(Characteristics_Points, 16) + 1 <= 16) {
                        binding.attractivenessValue.setText(String.valueOf(Integer.parseInt(binding.attractivenessValue.getText().toString()) - 1));
                        sharedPreferences.edit().putInt(Attractiveness, Integer.parseInt(binding.attractivenessValue.getText().toString())).
                                putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 16) + 1).apply();
                        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d",
                                sharedPreferences.getInt(Characteristics_Points, 16)));
                    } else {
                        binding.message.setText("Слишком маленькое значение характеристики...");
                    }
                    break;
            }
        }
    }

    private void settingAttractivenessInformation() {
        binding.characteristicName.setText("Привлекательность");
        binding.descriptionCharacteristics.setText("Представляет собой, захотят ли люди пойти за Вами или быть с Вами.\nОт неё зависят цена при торговле и реакция других на Ваши слова.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 7).apply();
    }

    private void settingWatchfulnessInformation() {
        binding.characteristicName.setText("Наблюдательность");
        binding.descriptionCharacteristics.setText("Эта характеристика показывает, увидите ли Вы иглу в стоге сена.\nОт неё зависят Ваша точность при стрельбе.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 6).apply();
    }

    private void settingLuckinessInformation() {
        binding.characteristicName.setText("Удачливость");
        binding.descriptionCharacteristics.setText("Определяет шанс выигрыша в лотерее.\nОт неё зависят шанс критического удара и шанс попадания в целом.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 5).apply();
    }

    private void settingMentalityInformation() {
        binding.characteristicName.setText("Ум");
        binding.descriptionCharacteristics.setText("Ваше интеллектуальное развитие является данной характеристикой.\nОт него зависят получаемый Вами опыт.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 4).apply();
    }

    private void settingDexterityInformation() {
        binding.characteristicName.setText("Сноровка");
        binding.descriptionCharacteristics.setText("Эта характеристика, являющаяся общим показателем Вашей гибкости, поворотливости, ловкости.\nОт неё зависят Ваши очки действия и вероятность уклонения.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 3).apply();
    }

    private void settingPhysiqueInformation() {
        binding.characteristicName.setText("Телосложение");
        binding.descriptionCharacteristics.setText("Худой Вы или полный, широкий или узкий - это представляет данная характеристика.\nОт него зависят переносимый Вами объём, Ваша живучесть и защищённость.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 2).apply();
    }

    private void settingStrengthInformation() {
        binding.characteristicName.setText("Сила");
        binding.descriptionCharacteristics.setText("Характеристика, показывающая Вашу мощь.\nОт неё зависят переносимый Вами вес и мощность Вашего удара.");
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(Current_Characteristic, 1).apply();
    }
}