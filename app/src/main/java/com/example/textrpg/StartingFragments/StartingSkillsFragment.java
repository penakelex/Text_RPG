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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.textrpg.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import com.example.textrpg.Databases.SkillsDatabase.SkillsDatabaseHelper;
import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingSkillsBinding;


public class StartingSkillsFragment extends Fragment {
    private FragmentStartingSkillsBinding binding;
    SkillsDatabaseHelper skillsDatabaseHelper;
    CharacteristicsDatabaseHelper databaseHelper;
    SQLiteDatabase skillDatabase, characteristicDatabase;
    private final String Main_Skills = "Main Skills", First_Visit_Skills = "First Visit Skills";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        skillsDatabaseHelper = new SkillsDatabaseHelper(getActivity());
        skillDatabase = skillsDatabaseHelper.getWritableDatabase();
        databaseHelper = new CharacteristicsDatabaseHelper(getActivity());
        characteristicDatabase = databaseHelper.getReadableDatabase();
        settingSkillsInDatabase(skillDatabase, characteristicDatabase);
        binding.lightWeapons.setOnClickListener(listener -> settingLightWeaponsInformation(skillDatabase));
        binding.heavyWeapons.setOnClickListener(listener -> settingHeavyWeaponsInformation(skillDatabase));
        binding.meleeWeapons.setOnClickListener(listener -> settingMeleeWeaponsInformation(skillDatabase));
        binding.communication.setOnClickListener(listener -> settingCommunicationInformation(skillDatabase));
        binding.trading.setOnClickListener(listener -> settingTradingInformation(skillDatabase));
        binding.survival.setOnClickListener(listener -> settingSurvivalInformation(skillDatabase));
        binding.medicine.setOnClickListener(listener -> settingMedicineInformation(skillDatabase));
        binding.scince.setOnClickListener(listener -> settingScienceInformation(skillDatabase));
        binding.repair.setOnClickListener(listener -> settingRepairInformation(skillDatabase));
        binding.chooseAsMainSkill.setOnClickListener(listener -> choosingAsMainSkill(skillDatabase));
        binding.buttonToAbilities.setOnClickListener(listener -> settingAbilitiesFragment());
        binding.buttonToCharateristics.setOnClickListener(listener -> settingCharacteristicsFragment());
    }

    private void settingCharacteristicsFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Main_Skills, 3) == 0) {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        } else {
            binding.message.setText("Вы ещё не определили все основные навыки...");
        }
    }

    private void settingAbilitiesFragment() {
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Main_Skills, 3) == 0) {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingTalentsFragment()).commit();
        } else {
            binding.message.setText("Вы ещё не определили все основные навыки...");
        }
    }

    @SuppressLint("DefaultLocale")
    private void choosingAsMainSkill(SQLiteDatabase database) {
        binding.message.setText("");
        int id = 0, idColumnIndex, valueColumnIndex, value = 0, isMainColumnIndex;
        boolean isMain = false, isChosen = true;
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (binding.skillName.getText().toString()) {
            case "Лёгкое оружие":
                id = 1;
                break;
            case "Тяжёлое оружие":
                id = 2;
                break;
            case "Оружие ближнего боя":
                id = 3;
                break;
            case "Общение":
                id = 4;
                break;
            case "Торговля":
                id = 5;
                break;
            case "Выживание":
                id = 6;
                break;
            case "Медицина":
                id = 7;
                break;
            case "Наука":
                id = 8;
                break;
            case "Ремонт":
                id = 9;
                break;
            default:
                binding.message.setText("Вы не выбрали навык");
                isChosen = false;
                break;
        }
        if (isChosen) {
            Cursor cursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
            cursor.moveToFirst();
            idColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID);
            valueColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
            isMainColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
            do {
                if (cursor.getInt(idColumnIndex) == id) {
                    value = cursor.getInt(valueColumnIndex);
                    isMain = cursor.getInt(isMainColumnIndex) == 1;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            Log.d("value && isMain", String.format("%d\n%s", value, String.valueOf(isMain)));
            int mainSkills = sharedPreferences.getInt(Main_Skills, 3);
            boolean isMainSkillChosen;
            if (isMain) {
                if (mainSkills + 1 <= 3) {
                    database.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                            SkillsDatabaseHelper.Table_Skills,
                            SkillsDatabaseHelper.KEY_Is_Main,
                            SkillsDatabaseHelper.KEY_ID, id));
                    database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            SkillsDatabaseHelper.Table_Skills,
                            SkillsDatabaseHelper.KEY_Value,
                            value / 2,
                            SkillsDatabaseHelper.KEY_ID,
                            id));
                    sharedPreferences.edit().putInt(Main_Skills, sharedPreferences.getInt(Main_Skills, 3) + 1).apply();
                    binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d", sharedPreferences.getInt(Main_Skills, 3)));
                    isMainSkillChosen = true;
                } else {
                    isMainSkillChosen = false;
                    binding.message.setText("Вы ещё не выбрали ни один основной навык");
                }
            } else {
                if (mainSkills - 1 >= 0) {
                    database.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                            SkillsDatabaseHelper.Table_Skills,
                            SkillsDatabaseHelper.KEY_Is_Main,
                            SkillsDatabaseHelper.KEY_ID, id));
                    database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            SkillsDatabaseHelper.Table_Skills,
                            SkillsDatabaseHelper.KEY_Value,
                            value * 2,
                            SkillsDatabaseHelper.KEY_ID,
                            id));
                    sharedPreferences.edit().putInt(Main_Skills, mainSkills - 1).apply();
                    binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d", sharedPreferences.getInt(Main_Skills, 3)));
                    isMainSkillChosen = true;
                } else {
                    isMainSkillChosen = false;
                    binding.message.setText("Вы уже выбрали максимальное количество основных навыков");
                }
            }
            if (isMainSkillChosen) {
                int newValue, newIdColumnIndex, newIsMainColumnIndex, newValueColumnIndex, newId;
                boolean newIsMain;
                Cursor afterCursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
                afterCursor.moveToFirst();
                newIdColumnIndex = afterCursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID);
                newIsMainColumnIndex = afterCursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
                newValueColumnIndex = afterCursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
                do {
                    newValue = afterCursor.getInt(newValueColumnIndex);
                    newIsMain = afterCursor.getInt(newIsMainColumnIndex) == 1;
                    newId = afterCursor.getInt(newIdColumnIndex);
                    if (newId == id) {
                        switch (newId) {
                            case 1:
                                binding.lightWeaponsValue.setText(String.valueOf(newValue));
                                break;
                            case 2:
                                binding.heavyWeaponsValue.setText(String.valueOf(newValue));
                                break;
                            case 3:
                                binding.meleeWeaponsValue.setText(String.valueOf(newValue));
                                break;
                            case 4:
                                binding.communicationValue.setText(String.valueOf(newValue));
                                break;
                            case 5:
                                binding.tradingValue.setText(String.valueOf(newValue));
                                break;
                            case 6:
                                binding.survivalValue.setText(String.valueOf(newValue));
                                break;
                            case 7:
                                binding.medicineValue.setText(String.valueOf(newValue));
                                break;
                            case 8:
                                binding.scinceValue.setText(String.valueOf(newValue));
                                break;
                            case 9:
                                binding.repairValue.setText(String.valueOf(newValue));
                                break;
                        }
                        if (!newIsMain) {
                            binding.chooseAsMainSkill.setText("Выбрать как основной навык");
                        } else {
                            binding.chooseAsMainSkill.setText("Выбрать как неосновной навык");
                        }
                        break;
                    }
                } while (afterCursor.moveToNext());
                afterCursor.close();
            }

        }
    }

    @SuppressLint({"Range", "DefaultLocale"})
    private void settingSkillsInDatabase(SQLiteDatabase database, SQLiteDatabase characteristicDatabase) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Cursor characteristicsCursor = characteristicDatabase.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        int characteristicsIdColumnIndex = characteristicsCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                characteristicsValueColumnIndex = characteristicsCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                strength = 0, physique = 0, dexterity = 0, mentality = 0, luckiness = 0, watchfulness = 0,
                attractiveness = 0;
        characteristicsCursor.moveToFirst();
        do {
            switch (characteristicsCursor.getInt(characteristicsIdColumnIndex)) {
                case 1:
                    strength = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 2:
                    physique = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 3:
                    dexterity = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 4:
                    mentality = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 5:
                    luckiness = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 6:
                    watchfulness = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
                case 7:
                    attractiveness = characteristicsCursor.getInt(characteristicsValueColumnIndex);
                    break;
            }
        } while (characteristicsCursor.moveToNext());
        characteristicsCursor.close();
        if (sharedPreferences.getBoolean(First_Visit_Skills, true)) {
            database.delete(SkillsDatabaseHelper.Table_Skills, null, null);
            sharedPreferences.edit().putBoolean(First_Visit_Skills, false).putInt(Main_Skills, 3).apply();

            ContentValues contentValues1 = new ContentValues(), contentValues2 = new ContentValues(),
                    contentValues3 = new ContentValues(), contentValues4 = new ContentValues(),
                    contentValues5 = new ContentValues(), contentValues6 = new ContentValues(),
                    contentValues7 = new ContentValues(), contentValues8 = new ContentValues(),
                    contentValues9 = new ContentValues();

            contentValues1.put(SkillsDatabaseHelper.KEY_ID, 1);
            contentValues1.put(SkillsDatabaseHelper.KEY_Name, "light weapons");
            contentValues1.put(SkillsDatabaseHelper.KEY_Value, watchfulness * 2 + luckiness + physique + 5);
            contentValues1.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues2.put(SkillsDatabaseHelper.KEY_ID, 2);
            contentValues2.put(SkillsDatabaseHelper.KEY_Name, "heavy weapons");
            contentValues2.put(SkillsDatabaseHelper.KEY_Value, strength * 2 + watchfulness + luckiness + 5);
            contentValues2.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues3.put(SkillsDatabaseHelper.KEY_ID, 3);
            contentValues3.put(SkillsDatabaseHelper.KEY_Name, "melee weapons");
            contentValues3.put(SkillsDatabaseHelper.KEY_Value, watchfulness + luckiness + physique + strength * 2 + 5);
            contentValues3.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues4.put(SkillsDatabaseHelper.KEY_ID, 4);
            contentValues4.put(SkillsDatabaseHelper.KEY_Name, "communication");
            contentValues4.put(SkillsDatabaseHelper.KEY_Value, attractiveness * 3 + luckiness + mentality + 5);
            contentValues4.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues5.put(SkillsDatabaseHelper.KEY_ID, 5);
            contentValues5.put(SkillsDatabaseHelper.KEY_Name, "trading");
            contentValues5.put(SkillsDatabaseHelper.KEY_Value, attractiveness * 3 + watchfulness + 5);
            contentValues5.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues6.put(SkillsDatabaseHelper.KEY_ID, 6);
            contentValues6.put(SkillsDatabaseHelper.KEY_Name, "survival");
            contentValues6.put(SkillsDatabaseHelper.KEY_Value, physique * 2 + dexterity + mentality + 5);
            contentValues6.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues7.put(SkillsDatabaseHelper.KEY_ID, 7);
            contentValues7.put(SkillsDatabaseHelper.KEY_Name, "medicine");
            contentValues7.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + 5);
            contentValues7.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues8.put(SkillsDatabaseHelper.KEY_ID, 8);
            contentValues8.put(SkillsDatabaseHelper.KEY_Name, "science");
            contentValues8.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + 5);
            contentValues8.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            contentValues9.put(SkillsDatabaseHelper.KEY_ID, 9);
            contentValues9.put(SkillsDatabaseHelper.KEY_Name, "repair");
            contentValues9.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + dexterity + 5);
            contentValues9.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues1);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues2);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues3);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues4);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues5);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues6);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues7);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues8);
            database.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues9);
        } else {
            boolean[] isMains = new boolean[9];
            Cursor booleanCursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
            int booleanIdColumnIndex = booleanCursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID),
                    booleanIsMainColumnIndex = booleanCursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
            booleanCursor.moveToFirst();
            do {
                switch (booleanCursor.getInt(booleanIdColumnIndex)) {
                    case 1:
                        isMains[0] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 2:
                        isMains[1] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 3:
                        isMains[2] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 4:
                        isMains[3] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 5:
                        isMains[4] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 6:
                        isMains[5] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 7:
                        isMains[6] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 8:
                        isMains[7] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                    case 9:
                        isMains[8] = booleanCursor.getInt(booleanIsMainColumnIndex) == 1;
                        break;
                }
            } while (booleanCursor.moveToNext());
            booleanCursor.close();
            int[] values = {isMains[0] ? 2 * (watchfulness * 2 + luckiness + physique + 5) : (watchfulness * 2 + luckiness + physique + 5),
                    isMains[1] ? 2 * (strength * 2 + watchfulness + luckiness + 5) : (strength * 2 + watchfulness + luckiness + 5),
                    isMains[2] ? 2 * (watchfulness + luckiness + physique + strength * 2 + 5) : (watchfulness + luckiness + physique + strength * 2 + 5),
                    isMains[3] ? 2 * (attractiveness * 3 + luckiness + mentality + 5) : (attractiveness * 3 + luckiness + mentality + 5),
                    isMains[4] ? 2 * (attractiveness * 3 + watchfulness + 5) : (attractiveness * 3 + watchfulness + 5),
                    isMains[5] ? 2 * (physique * 2 + dexterity + mentality + 5) : (physique * 2 + dexterity + mentality + 5),
                    isMains[6] ? 2 * (mentality * 2 + watchfulness + 5) : (mentality * 2 + watchfulness + 5),
                    isMains[7] ? 2 * (mentality * 2 + watchfulness + 5) : (mentality * 2 + watchfulness + 5),
                    isMains[8] ? 2 * (mentality * 2 + watchfulness + dexterity + 5) : (mentality * 2 + watchfulness + dexterity + 5)};

            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[0],
                    SkillsDatabaseHelper.KEY_ID,
                    1));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[1],
                    SkillsDatabaseHelper.KEY_ID,
                    2));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[2],
                    SkillsDatabaseHelper.KEY_ID,
                    3));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[3],
                    SkillsDatabaseHelper.KEY_ID,
                    4));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[4],
                    SkillsDatabaseHelper.KEY_ID,
                    5));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[5],
                    SkillsDatabaseHelper.KEY_ID,
                    6));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[6],
                    SkillsDatabaseHelper.KEY_ID,
                    7));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[7],
                    SkillsDatabaseHelper.KEY_ID,
                    8));
            database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    SkillsDatabaseHelper.Table_Skills,
                    SkillsDatabaseHelper.KEY_Value,
                    values[8],
                    SkillsDatabaseHelper.KEY_ID,
                    9));
        }
        binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d",
                sharedPreferences.getInt(Main_Skills, 3)));
        int idColumnIndex, nameColumnIndex, valueColumnIndex, isMainColumnIndex,
                id, value;
        Cursor cursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        idColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID);
        nameColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Name);
        valueColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
        isMainColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
        cursor.moveToFirst();
        do {

            id = cursor.getInt(idColumnIndex);
            value = cursor.getInt(valueColumnIndex);
            Log.d("Записанные начальные данные",
                    String.format("%d - id\n%s - name\n%d - value\n%d - is main",
                            id, cursor.getString(nameColumnIndex),
                            value, cursor.getInt(isMainColumnIndex)));
            switch (id) {
                case 1:
                    binding.lightWeaponsValue.setText(String.valueOf(value));
                    break;
                case 2:
                    binding.heavyWeaponsValue.setText(String.valueOf(value));
                    break;
                case 3:
                    binding.meleeWeaponsValue.setText(String.valueOf(value));
                    break;
                case 4:
                    binding.communicationValue.setText(String.valueOf(value));
                    break;
                case 5:
                    binding.tradingValue.setText(String.valueOf(value));
                    break;
                case 6:
                    binding.survivalValue.setText(String.valueOf(value));
                    break;
                case 7:
                    binding.medicineValue.setText(String.valueOf(value));
                    break;
                case 8:
                    binding.scinceValue.setText(String.valueOf(value));
                    break;
                case 9:
                    binding.repairValue.setText(String.valueOf(value));
                    break;
            }
        } while (cursor.moveToNext());
        cursor.close();
        settingLightWeaponsInformation(database);
    }

    private void settingRepairInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 9);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Ремонт");
        binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + Сноровка + 5.");
        binding.descriptionSkills.setText("Часть науки не с теоретической части."); //
    }

    private void settingScienceInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 8);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Наука");
        binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + 5.");
        binding.descriptionSkills.setText("Сочетание из многих наук: математики, физики, химии, биологии и многих других."); //
    }

    private void settingMedicineInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 7);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Медицина");
        binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + 5.");
        binding.descriptionSkills.setText("Общие и углублённые знания лечения."); //
    }

    private void settingSurvivalInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 6);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Выживание");
        binding.baseValue.setText("Базовое значение: Телосложение × 2 + Сноровка + Ум + 5.");
        binding.descriptionSkills.setText("Практические знания правил жизни в космосе и на других планетах, сложившиеся из теоретических."); //
    }

    private void settingTradingInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 5);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Торговля");
        binding.baseValue.setText("Базовое значение: Привлекательность × 3 + Наблюдательность + 5.");
        binding.descriptionSkills.setText("Исскуство сделать для себя сделку, покупку, продажу, бартер выгоднее."); //
    }

    private void settingCommunicationInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 4);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Общение");
        binding.baseValue.setText("Базовое значение: Привлекательность × 3 + Удачливость + Ум + 5.");
        binding.descriptionSkills.setText("Отображает Ваше мастерство в ораторском искусстве: убеждении других."); //
    }

    private void settingMeleeWeaponsInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 3);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Оружие ближнего боя");
        binding.baseValue.setText("Базовое значение: Наблюдательность + Удачливость + Телосложение + Сила × 2 + 5.");
        binding.descriptionSkills.setText("Ближний бой с оружием и без, что говорит о Вашем умении использовать Ваши руки и ноги не только в мирных целях.");
    }

    private void settingHeavyWeaponsInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 2);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Тяжёлое оружие");
        binding.baseValue.setText("Базовое значение: Сила × 2 + Наблюдательность + Удачливость + 5.");
        binding.descriptionSkills.setText("Ваше умение управления на самом деле нелёгких орудий."); //Что по оружию, я ещё не знаю

    }

    private void settingLightWeaponsInformation(SQLiteDatabase database) {
        boolean isMain = getIsMainSkill(database, 1);
        binding.chooseAsMainSkill.setText(isMain ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
        binding.skillName.setText("Лёгкое оружие");
        binding.baseValue.setText("Базовое значение: Наблюдательность × 2 + Удачливость + Телосложение + 5.");
        binding.descriptionSkills.setText("Отображает Ваше умение пользоваться лёгким оружием.");
    }
    private boolean getIsMainSkill(SQLiteDatabase database, int ID) {
        int idColumnIndex, isMainColumnIndex;
        boolean isMain = false;
        Cursor cursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        cursor.moveToFirst();
        idColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID);
        isMainColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
        do {
            if (cursor.getInt(idColumnIndex) == ID) {
                isMain = cursor.getInt(isMainColumnIndex) == 1;
                break;
            }
        } while (cursor.moveToNext());
        cursor.close();
        return isMain;
    }
}