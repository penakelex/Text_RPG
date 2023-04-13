package com.example.textrpg.StartingFragments;

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
import com.example.textrpg.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import com.example.textrpg.Databases.SkillsDatabase.SkillsDatabaseHelper;
import com.example.textrpg.Databases.TalentsDatabase.TalentsDatabaseHelper;
import com.example.textrpg.databinding.FragmentStartingTalentsBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;


public class StartingTalentsFragment extends Fragment {
    private FragmentStartingTalentsBinding startingTalentsBinding;
    private TalentsDatabaseHelper talentsDatabaseHelper;
    private SkillsDatabaseHelper skillsDatabaseHelper;
    private CharacteristicsDatabaseHelper characteristicsDatabaseHelper;
    private OtherInformationDatabaseHelper infoDatabaseHelper;
    private final String First_Visit_Talents = "First Visit Talents";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startingTalentsBinding = FragmentStartingTalentsBinding.inflate(inflater, container, false);
        return startingTalentsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        talentsDatabaseHelper = new TalentsDatabaseHelper(getActivity());
        skillsDatabaseHelper = new SkillsDatabaseHelper(getActivity());
        characteristicsDatabaseHelper = new CharacteristicsDatabaseHelper(getActivity());
        SQLiteDatabase talentsDatabase = talentsDatabaseHelper.getWritableDatabase(),
                skillsDatabase = skillsDatabaseHelper.getWritableDatabase(),
                characteristicDatabase = characteristicsDatabaseHelper.getWritableDatabase(),
                infoDatabase = infoDatabaseHelper.getWritableDatabase();
        settingStartingValues(talentsDatabase, infoDatabase, characteristicDatabase, skillsDatabase);
        startingTalentsBinding.singer.setOnClickListener(listener -> settingSingerInformation(talentsDatabase));
        startingTalentsBinding.bull.setOnClickListener(listener -> settingBullInformation(talentsDatabase));
        startingTalentsBinding.strongKick.setOnClickListener(listener -> settingStrongKickInformation(talentsDatabase));
        startingTalentsBinding.experienced.setOnClickListener(listener -> settingExpiriencedInformation(talentsDatabase));
        startingTalentsBinding.trained.setOnClickListener(listener -> settingTrainedInformation(talentsDatabase));
        startingTalentsBinding.heavyweight.setOnClickListener(listener -> settingHeavyweightInformation(talentsDatabase));
        startingTalentsBinding.kindOne.setOnClickListener(listener -> settingKindOneInformation(talentsDatabase));
        //startingTalentsBinding.chooseTalent.setOnClickListener(listener -> choosingTalent());
    }


    private void settingKindOneInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(7, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingHeavyweightInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(6, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingTrainedInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(5, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingExpiriencedInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(4, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingStrongKickInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(3, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingBullInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(2, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("");
        startingTalentsBinding.meaning.setText("");
        startingTalentsBinding.shortDescription.setText("");
    }

    private void settingSingerInformation(SQLiteDatabase talentsDatabase) {
        startingTalentsBinding.chooseTalent.setText(!getIsHaving(1, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Певец");
        startingTalentsBinding.meaning.setText("Привлекательность: +1");
        startingTalentsBinding.shortDescription.setText("У Вас от природы красивейший голос, за счёт чего Вы становитесь более привлекательными для окружающих.");
    }

    private boolean getIsHaving(int ID, SQLiteDatabase talentsDatabase) {
        Cursor havingTalentCursor = talentsDatabase.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        havingTalentCursor.moveToFirst();
        int idColumnIndex = havingTalentCursor.getColumnIndex(TalentsDatabaseHelper.KEY_ID),
                havingColumnIndex = havingTalentCursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        boolean isHaving = false;
        do {
            if (havingTalentCursor.getInt(idColumnIndex) == ID) {
                isHaving = havingTalentCursor.getInt(havingColumnIndex) == 1;
                break;
            }
        } while (havingTalentCursor.moveToNext());
        havingTalentCursor.close();
        return isHaving;
    }

    private void settingStartingValues(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
            sharedPreferences.edit().putBoolean(First_Visit_Talents, false).apply();
            settingStartValuesInTalentsDatabase(talentsDatabase);
            settingStartValuesInInfoDatabase(infoDatabase, characteristicsDatabase, skillsDatabase);
        }
    }

    private void settingStartValuesInInfoDatabase(SQLiteDatabase infoDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        int[] characteristics = getCharacteristicsValueArray(characteristicsDatabase);
        boolean[] isMains = getIsMainValueArray(skillsDatabase);
        int[] skills = getSkillsValueArray(skillsDatabase);
        ContentValues[] contentValues = new ContentValues[6];

        for (int id = 1; id <= 6; id++)
            contentValues[id - 1].put(OtherInformationDatabaseHelper.KEY_ID, id);

        contentValues[0].put(OtherInformationDatabaseHelper.KEY_Name, "armor_class");
        contentValues[1].put(OtherInformationDatabaseHelper.KEY_Name, "ap");
        contentValues[2].put(OtherInformationDatabaseHelper.KEY_Name, "load_capacity");
        contentValues[3].put(OtherInformationDatabaseHelper.KEY_Name, "carry_volume");
        contentValues[4].put(OtherInformationDatabaseHelper.KEY_Name, "melee_damage");
        contentValues[5].put(OtherInformationDatabaseHelper.KEY_Name, "critical_hit");

        contentValues[0].put(OtherInformationDatabaseHelper.KEY_Value, 0);
        contentValues[1].put(OtherInformationDatabaseHelper.KEY_Value, Math.max(characteristics[2] + characteristics[1], 5));
        contentValues[2].put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 10 + characteristics[2] * 3 + 20);
        contentValues[3].put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 2000 + characteristics[2] * 1000 + 10000); // В кубических сантиметрах
        contentValues[4].put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] + characteristics[2] / 2);
        contentValues[5].put(OtherInformationDatabaseHelper.KEY_Value, characteristics[4] * 2 + characteristics[2] + characteristics[5] / 2);

        for (ContentValues contentValue : contentValues)
            infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValue);
        Log.d("set values information database ended", "set values information database ended");
    }

    private int[] getSkillsValueArray(SQLiteDatabase skillsDatabase) {
        Cursor skillsValueCursor = skillsDatabase.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        skillsValueCursor.moveToFirst();
        int skillValueColumnIndex = skillsValueCursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
        ArrayList<Integer> skillsList = new ArrayList<>();
        do {
            skillsList.add(skillsValueCursor.getInt(skillValueColumnIndex));
        } while (skillsValueCursor.moveToNext());
        skillsValueCursor.close();
        int[] values = new int[skillsList.size()];
        for (int i = 0; i < skillsList.size(); i++) values[i] = skillsList.get(i);
        return values;
    }

    private boolean[] getIsMainValueArray(SQLiteDatabase skillsDatabase) {
        Cursor isMainCursor = skillsDatabase.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        isMainCursor.moveToFirst();
        int isMainColumnIndex = isMainCursor.getColumnIndex(SkillsDatabaseHelper.KEY_Is_Main);
        ArrayList<Boolean> isMainList = new ArrayList<>();
        do {
            isMainList.add(isMainCursor.getInt(isMainColumnIndex) == 1);
        } while (isMainCursor.moveToNext());
        boolean[] isMains = new boolean[isMainList.size()];
        for (int i = 0; i < isMainList.size(); i++) isMains[i] = isMainList.get(i);
        isMainCursor.close();
        return isMains;
    }

    private int[] getCharacteristicsValueArray(SQLiteDatabase characteristicsDatabase) {
        Cursor characteristicsValueCursor = characteristicsDatabase.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        characteristicsValueCursor.moveToFirst();
        int valueColumnIndex = characteristicsValueCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        ArrayList<Integer> characteristicsList = new ArrayList<>();
        do {
            characteristicsList.add(characteristicsValueCursor.getInt(valueColumnIndex));
        } while (characteristicsValueCursor.moveToNext());
        int[] characteristicsValue = new int[characteristicsList.size()];
        for (int i = 0; i < characteristicsList.size(); i++)
            characteristicsValue[i] = characteristicsList.get(i);
        characteristicsValueCursor.close();
        return characteristicsValue;
    }

    private void settingStartValuesInTalentsDatabase(SQLiteDatabase talentsDatabase) {
        ContentValues[] contentValues = new ContentValues[7];
        Log.d("set started", "set started");
        for (int id = 1; id <= 7; id++) contentValues[id - 1].put(TalentsDatabaseHelper.KEY_ID, id);

        contentValues[0].put(TalentsDatabaseHelper.KEY_Name, "singer");
        contentValues[1].put(TalentsDatabaseHelper.KEY_Name, "bull");
        contentValues[2].put(TalentsDatabaseHelper.KEY_Name, "strongKick");
        contentValues[3].put(TalentsDatabaseHelper.KEY_Name, "experienced");
        contentValues[4].put(TalentsDatabaseHelper.KEY_Name, "trained");
        contentValues[5].put(TalentsDatabaseHelper.KEY_Name, "heavyweight");
        contentValues[6].put(TalentsDatabaseHelper.KEY_Name, "kindOne");

        for (int i = 0; i < 7; i++) contentValues[i].put(TalentsDatabaseHelper.KEY_Having, 0);

        for (int i = 0; i < 7; i++)
            talentsDatabase.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues[i]);
        Log.d("set values talents database ended", "set values talents database ended");
    }
}