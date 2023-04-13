package com.example.textrpg.StartingFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.textrpg.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingCharacteristicsBinding;

public class StartingCharacteristicsFragment extends Fragment {
    private FragmentStartingCharacteristicsBinding binding;
    SharedPreferences sharedPreferences;
    private final String Characteristics_Points = "Characteristics Points",
            First_Visit_Characteristics = "First Visit Characteristics";
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
        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d", getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Characteristics_Points, 2)));
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
            sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            int id = getIdByText();
            boolean characteristicChosen = id != 0;
            if (characteristicChosen) {
                Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
                cursor.moveToFirst();
                int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                        valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                        value = 0;
                do {
                    if (cursor.getInt(idColumnIndex) == id) {
                        value = cursor.getInt(valueColumnIndex);
                        break;
                    }
                } while (cursor.moveToNext());
                cursor.close();
                if (value - 1 >= 0) {
                    database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            value - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            id));
                    sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) + 1).apply();
                    Cursor afterCursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
                    afterCursor.moveToFirst();
                    int newIdColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                            newValueColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                            newValue = 0;
                    do {
                        if (afterCursor.getInt(newIdColumnIndex) == id) {
                            newValue = afterCursor.getInt(newValueColumnIndex);
                            break;
                        }
                    } while (afterCursor.moveToNext());
                    afterCursor.close();
                    setNewValue(newValue, id);
                } else {
                    binding.message.setText("Слишком маленькое значение характеристики...");
                }
            }
    }

    @SuppressLint("DefaultLocale")
    private void raising(SQLiteDatabase database) {
        binding.message.setText("");
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int id = getIdByText();
        boolean characteristicChosen = id != 0;
        if (characteristicChosen) {
            Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
            int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                    valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                    value = 0;
            cursor.moveToFirst();
            do {
                if (cursor.getInt(idColumnIndex) == id) {
                    value = cursor.getInt(valueColumnIndex);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (value + 1 <= 5 && sharedPreferences.getInt(Characteristics_Points, 2) - 1 >= 0) {
                database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value + 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        id));
                sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) - 1).apply();
                Cursor afterCursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
                int newIdColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                        newValueColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                        newValue = 0;
                afterCursor.moveToFirst();
                do {
                    if (afterCursor.getInt(newIdColumnIndex) == id) {
                        newValue = afterCursor.getInt(newValueColumnIndex);
                        break;
                    }
                } while (afterCursor.moveToNext());
                afterCursor.close();
                setNewValue(newValue, id);
            } else {
                if (value + 1 > 5) {
                    binding.message.setText("Слишком большое значение характеристики...");
                } else {
                    binding.message.setText("Не хватет очков характеристик...");
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void settingCharacteristicInDatabase(SQLiteDatabase database) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(First_Visit_Characteristics, true)) {
            database.delete(CharacteristicsDatabaseHelper.Table_Characteristics, null, null);
            sharedPreferences.edit().putBoolean(First_Visit_Characteristics, false).
                    putInt(Characteristics_Points, 2).apply();

            ContentValues contentValuesStrength = new ContentValues(),
                    contentValuesPhysique = new ContentValues(),
                    contentValuesDexterity = new ContentValues(),
                    contentValuesMentality = new ContentValues(),
                    contentValuesLuckiness = new ContentValues(),
                    contentValuesWatchfulness = new ContentValues(),
                    contentValuesAttractiveness = new ContentValues();

            contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_ID, 1);
            contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_Name, "strength");
            contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_ID, 2);
            contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_Name, "physique");
            contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_ID, 3);
            contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_Name, "dexterity");
            contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_ID, 4);
            contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_Name, "mentality");
            contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_ID, 5);
            contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_Name, "luckiness");
            contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_ID, 6);
            contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_Name, "watchfulness");
            contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_ID, 7);
            contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_Name, "attractiveness");
            contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesStrength);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesPhysique);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesDexterity);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesMentality);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesLuckiness);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesWatchfulness);
            database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesAttractiveness);
        }

        int idColumnIndex, valueColumnIndex, value = 0, id = 0;
        Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID);
        valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            try {
                id = cursor.getInt(idColumnIndex);
                value = cursor.getInt(valueColumnIndex);
                Log.d("value, id", value + " " + id);
                switch (id) {
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
            } catch (Exception exception) {
                Log.d("characteristic exception", String.valueOf(exception));
            }
        } while (cursor.moveToNext());
        cursor.close();
        binding.characteristicsPoints.setText(String.format("Осталось очков характеристик: %d", sharedPreferences.getInt(Characteristics_Points, 2)));
        settingStrengthInformation();
    }

    private void settingSkillsFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        }
    }

    private void settingMainInfoFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Characteristics_Points, 2) > 0) {
            binding.message.setText("Вы не можете перейти на другую страницу, не распределив все очки характеристик...");
        } else {
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