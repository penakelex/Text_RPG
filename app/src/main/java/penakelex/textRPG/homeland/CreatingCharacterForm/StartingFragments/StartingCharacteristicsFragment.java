package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

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

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;


import penakelex.textRPG.homeland.Main.Constants;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingCharacteristicsBinding;

public class StartingCharacteristicsFragment extends Fragment {
    private FragmentStartingCharacteristicsBinding binding;
    SharedPreferences sharedPreferences;

    SQLiteDatabase database;
    CharacteristicsDatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingCharacteristicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new CharacteristicsDatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();
        settingCharacteristicInDatabase(database);
        binding.strength.setOnClickListener(listener -> settingStrengthInformation());
        binding.physique.setOnClickListener(listener -> settingPhysiqueInformation());
        binding.dexterity.setOnClickListener(listener -> settingDexterityInformation());
        binding.mentality.setOnClickListener(listener -> settingMentalityInformation());
        binding.luckiness.setOnClickListener(listener -> settingLuckinessInformation());
        binding.watchfulness.setOnClickListener(listener -> settingWatchfulnessInformation());
        binding.attractiveness.setOnClickListener(listener -> settingAttractivenessInformation());
        binding.downgrade.setOnClickListener(listener -> downgrading(database));
        binding.raise.setOnClickListener(listener -> raising(database));
        binding.buttonToMainInfo.setOnClickListener(listener -> settingMainInfoFragment());
        binding.buttonToSkills.setOnClickListener(listener -> settingSkillsFragment());
    }

    @SuppressLint("DefaultLocale")
    private void setNewValue(int value, int ID) {
        switch (ID) {
            case 1:
                binding.strengthValue.setText(String.valueOf(value));
                break;
            case 2:
                binding.physiqueValue.setText(String.valueOf(value));
                break;
            case 3:
                binding.dexterityValue.setText(String.valueOf(value));
                break;
            case 4:
                binding.mentalityValue.setText(String.valueOf(value));
                break;
            case 5:
                binding.luckinessValue.setText(String.valueOf(value));
                break;
            case 6:
                binding.watchfulnessValue.setText(String.valueOf(value));
                break;
            case 7:
                binding.attractivenessValue.setText(String.valueOf(value));
                break;
        }
        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d", getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Characteristics_Points, 2)));
    }

    private int getIdByText() {
        int id = 0;
        switch (binding.characteristicName.getText().toString()) {
            case "Сила":
                id = 1;
                break;
            case "Телосложение":
                id = 2;
                break;
            case "Сноровка":
                id = 3;
                break;
            case "Ум":
                id = 4;
                break;
            case "Удачливость":
                id = 5;
                break;
            case "Наблюдательность":
                id = 6;
                break;
            case "Привлекательность":
                id = 7;
                break;
            default:
                binding.message.setText("Вы не выбрали характеристику");
                break;
        }
        return id;
    }

    @SuppressLint("DefaultLocale")
    private void downgrading(SQLiteDatabase database) {
        binding.message.setText("");
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag,Context.MODE_PRIVATE);
        int id = getIdByText(), result = CharacteristicsDatabase.downgradeCharacteristic(database, id);
        switch (result) {
            case 1:
                sharedPreferences.edit().putInt(Constants.Characteristics_Points, sharedPreferences.getInt(Constants.Characteristics_Points, 2) + 1).apply();
                setNewValue(CharacteristicsDatabase.getNewValue(database, id), id);
                break;
            case 2:
                binding.message.setText("Слишком маленькое значение характеристики...");
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private void raising(SQLiteDatabase database) {
        binding.message.setText("");
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag,Context.MODE_PRIVATE);
        int id = getIdByText(), result = CharacteristicsDatabase.raiseCharacteristic(database,sharedPreferences, id);
        switch (result) {
            case 1:
                sharedPreferences.edit().putInt(Constants.Characteristics_Points, sharedPreferences.getInt(Constants.Characteristics_Points, 2) - 1).apply();
                setNewValue(CharacteristicsDatabase.getNewValue(database, id), id);
                break;
            case 2:
                binding.message.setText("Слишком большое значение характеристики...");
                break;
            case 3:
                binding.message.setText("Не хватет очков характеристик...");
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private void settingCharacteristicInDatabase(SQLiteDatabase database) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag,Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.First_Visit_Characteristics, true)) {
            sharedPreferences.edit().putBoolean(Constants.First_Visit_Characteristics, false).
                    putInt(Constants.Characteristics_Points, 2).apply();
            CharacteristicsDatabase.settingStartValuesInDatabase(database);
        }
        int[] values = CharacteristicsDatabase.getCharacteristicsValues(database);
        binding.strengthValue.setText(String.valueOf(values[0]));
        binding.physiqueValue.setText(String.valueOf(values[1]));
        binding.dexterityValue.setText(String.valueOf(values[2]));
        binding.mentalityValue.setText(String.valueOf(values[3]));
        binding.luckinessValue.setText(String.valueOf(values[4]));
        binding.watchfulnessValue.setText(String.valueOf(values[5]));
        binding.attractivenessValue.setText(String.valueOf(values[6]));
        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d", sharedPreferences.getInt(Constants.Characteristics_Points, 2)));
        settingStrengthInformation();
    }

    private void settingSkillsFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag,Context.MODE_PRIVATE).getInt(Constants.Characteristics_Points, 2) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        }
    }

    private void settingMainInfoFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag,Context.MODE_PRIVATE).getInt(Constants.Characteristics_Points, 2) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingInformationFragment()).commit();
        }
    }


    private void settingAttractivenessInformation() {
        binding.characteristicName.setText("Привлекательность");
        binding.descriptionCharacteristics.setText("Представляет собой, захотят ли люди пойти за Вами или быть с Вами.\nОт неё зависят цена при торговле и реакция других на Ваши слова.");
    }

    private void settingWatchfulnessInformation() {
        binding.characteristicName.setText("Наблюдательность");
        binding.descriptionCharacteristics.setText("Эта характеристика показывает, увидите ли Вы иглу в стоге сена.\nОт неё зависят Ваша точность при стрельбе.");
    }

    private void settingLuckinessInformation() {
        binding.characteristicName.setText("Удачливость");
        binding.descriptionCharacteristics.setText("Определяет шанс выигрыша в лотерее.\nОт неё зависят шанс критического удара и шанс попадания в целом.");
    }

    private void settingMentalityInformation() {
        binding.characteristicName.setText("Ум");
        binding.descriptionCharacteristics.setText("Ваше интеллектуальное развитие является данной характеристикой.\nОт него зависят получаемый Вами опыт и получаемое количество очков навыков.");
    }

    private void settingDexterityInformation() {
        binding.characteristicName.setText("Сноровка");
        binding.descriptionCharacteristics.setText("Эта характеристика, являющаяся общим показателем Вашей гибкости, поворотливости, ловкости.\nОт неё зависят Ваши очки действия и вероятность уклонения.");
    }

    private void settingPhysiqueInformation() {
        binding.characteristicName.setText("Телосложение");
        binding.descriptionCharacteristics.setText("Худой Вы или полный, широкий или узкий - это представляет данная характеристика.\nОт него зависят переносимый Вами объём, Ваша живучесть и защищённость.");
    }

    private void settingStrengthInformation() {
        binding.characteristicName.setText("Сила");
        binding.descriptionCharacteristics.setText("Характеристика, показывающая Вашу мощь.\nОт неё зависят переносимый Вами вес и мощность Вашего удара.");
    }
}