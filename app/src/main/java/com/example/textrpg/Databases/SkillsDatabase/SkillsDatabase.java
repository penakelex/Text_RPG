package com.example.textrpg.Databases.SkillsDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.textrpg.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import com.example.textrpg.Databases.TalentsDatabase.TalentsDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SkillsDatabase {
    @SuppressLint("DefaultLocale")
    public static void setSkillValueIsMain(SQLiteDatabase database, int ID, int value) {
        database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                value * 2,
                SkillsDatabaseHelper.KEY_ID,
                ID));
    }

    public static void setSkillIsMain(SQLiteDatabase database, int ID) {
        database.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Is_Main,
                SkillsDatabaseHelper.KEY_ID, ID));
    }

    public static void setSkillNotIsMain(SQLiteDatabase database, int ID) {
        database.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Is_Main,
                SkillsDatabaseHelper.KEY_ID, ID));
    }

    @SuppressLint("DefaultLocale")
    public static void setSKillValueNotMain(SQLiteDatabase database, int ID, int value) {
        database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                value / 2,
                SkillsDatabaseHelper.KEY_ID,
                ID));
    }

    public static int getValueSkill(SQLiteDatabase database, int ID) {
        int idColumnIndex, valueColumnIndex, value = 0;
        Cursor cursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        cursor.moveToFirst();
        idColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_ID);
        valueColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
        do {
            if (cursor.getInt(idColumnIndex) == ID) {
                value = cursor.getInt(valueColumnIndex);
                break;
            }
        } while (cursor.moveToNext());
        cursor.close();
        return value;
    }

    public static boolean getIsMainSkill(SQLiteDatabase database, int ID) {
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

    public static int[] getSkillValues(SQLiteDatabase database) {
        int valueColumnIndex;
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.query(SkillsDatabaseHelper.Table_Skills, null, null, null, null, null, null);
        valueColumnIndex = cursor.getColumnIndex(SkillsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            list.add(cursor.getInt(valueColumnIndex));
        } while (cursor.moveToNext());
        cursor.close();
        int[] values = new int[list.size()];
        for (int i = 0; i < list.size(); i++) values[i] = list.get(i);
        return values;
    }

    @SuppressLint("DefaultLocale")
    public static void settingNotStartingSkillsInDatabase(SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase talentsDatabase) {
        int[] values = CharacteristicsDatabase.getCharacteristicsValues(characteristicsDatabase),
        intSkills = {4, 5, 7, 8, 9}, fightSkills = {1, 2, 3};
        int strength = values[0], physique = values[1], dexterity = values[2], mentality = values[3],
                luckiness = values[4], watchfulness = values[5], attractiveness = values[6], number;
        boolean[] isMains = SkillsDatabase.getIsMains(skillsDatabase);
        int[] values1 = {isMains[0] ? 2 * (watchfulness * 2 + luckiness + physique + 10) : (watchfulness * 2 + luckiness + physique + 10),
                isMains[1] ? 2 * (strength * 2 + watchfulness + luckiness + 10) : (strength * 2 + watchfulness + luckiness + 10),
                isMains[2] ? 2 * (watchfulness + luckiness + physique + strength * 2 + 10) : (watchfulness + luckiness + physique + strength * 2 + 10),
                isMains[3] ? 2 * (attractiveness * 3 + luckiness + mentality + 10) : (attractiveness * 3 + luckiness + mentality + 10),
                isMains[4] ? 2 * (attractiveness * 3 + watchfulness + 10) : (attractiveness * 3 + watchfulness + 10),
                isMains[5] ? 2 * (physique * 2 + dexterity + mentality + 10) : (physique * 2 + dexterity + mentality + 10),
                isMains[6] ? 2 * (mentality * 2 + watchfulness + 10) : (mentality * 2 + watchfulness + 10),
                isMains[7] ? 2 * (mentality * 2 + watchfulness + 10) : (mentality * 2 + watchfulness + 10),
                isMains[8] ? 2 * (mentality * 2 + watchfulness + dexterity + 10) : (mentality * 2 + watchfulness + dexterity + 10)};
        for (int i = 0; i < values1.length; i++) {
            values1[i] += TalentsDatabase.isHaving(4, talentsDatabase) ? (isMains[i] ? 20 : 10) : 0;
            values1[i] -= TalentsDatabase.isHaving(5, talentsDatabase) ? (isMains[i] ? 20 : 10) : 0;
            number = i + 1;
            values1[i] += TalentsDatabase.isHaving(7, talentsDatabase) ? (Arrays.asList(intSkills).contains(number) ? isMains[i] ? 10 : 5 : Arrays.asList(fightSkills).contains(number) ? isMains[i] ? -10 : -5 : 0) : 0;
        }
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[0],
                SkillsDatabaseHelper.KEY_ID,
                1));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[1],
                SkillsDatabaseHelper.KEY_ID,
                2));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[2],
                SkillsDatabaseHelper.KEY_ID,
                3));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[3],
                SkillsDatabaseHelper.KEY_ID,
                4));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[4],
                SkillsDatabaseHelper.KEY_ID,
                5));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[5],
                SkillsDatabaseHelper.KEY_ID,
                6));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[6],
                SkillsDatabaseHelper.KEY_ID,
                7));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[7],
                SkillsDatabaseHelper.KEY_ID,
                8));
        skillsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                SkillsDatabaseHelper.Table_Skills,
                SkillsDatabaseHelper.KEY_Value,
                values1[8],
                SkillsDatabaseHelper.KEY_ID,
                9));
    }

    public static boolean[] getIsMains(SQLiteDatabase database) {
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
        return isMains;
    }

    public static void settingStartingSkillsInDatabase(SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase) {
        skillsDatabase.delete(SkillsDatabaseHelper.Table_Skills, null, null);
        int[] values = CharacteristicsDatabase.getCharacteristicsValues(characteristicsDatabase);
        int strength = values[0], physique = values[1], dexterity = values[2], mentality = values[3],
                luckiness = values[4], watchfulness = values[5], attractiveness = values[6];

        ContentValues contentValues1 = new ContentValues(), contentValues2 = new ContentValues(),
                contentValues3 = new ContentValues(), contentValues4 = new ContentValues(),
                contentValues5 = new ContentValues(), contentValues6 = new ContentValues(),
                contentValues7 = new ContentValues(), contentValues8 = new ContentValues(),
                contentValues9 = new ContentValues();

        contentValues1.put(SkillsDatabaseHelper.KEY_ID, 1);
        contentValues1.put(SkillsDatabaseHelper.KEY_Name, "light weapons");
        contentValues1.put(SkillsDatabaseHelper.KEY_Value, watchfulness * 2 + luckiness + physique + 10);
        contentValues1.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues2.put(SkillsDatabaseHelper.KEY_ID, 2);
        contentValues2.put(SkillsDatabaseHelper.KEY_Name, "heavy weapons");
        contentValues2.put(SkillsDatabaseHelper.KEY_Value, strength * 2 + watchfulness + luckiness + 10);
        contentValues2.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues3.put(SkillsDatabaseHelper.KEY_ID, 3);
        contentValues3.put(SkillsDatabaseHelper.KEY_Name, "melee weapons");
        contentValues3.put(SkillsDatabaseHelper.KEY_Value, watchfulness + luckiness + physique + strength * 2 + 10);
        contentValues3.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues4.put(SkillsDatabaseHelper.KEY_ID, 4);
        contentValues4.put(SkillsDatabaseHelper.KEY_Name, "communication");
        contentValues4.put(SkillsDatabaseHelper.KEY_Value, attractiveness * 3 + luckiness + mentality + 10);
        contentValues4.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues5.put(SkillsDatabaseHelper.KEY_ID, 5);
        contentValues5.put(SkillsDatabaseHelper.KEY_Name, "trading");
        contentValues5.put(SkillsDatabaseHelper.KEY_Value, attractiveness * 3 + watchfulness + 10);
        contentValues5.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues6.put(SkillsDatabaseHelper.KEY_ID, 6);
        contentValues6.put(SkillsDatabaseHelper.KEY_Name, "survival");
        contentValues6.put(SkillsDatabaseHelper.KEY_Value, physique * 2 + dexterity + mentality + 10);
        contentValues6.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues7.put(SkillsDatabaseHelper.KEY_ID, 7);
        contentValues7.put(SkillsDatabaseHelper.KEY_Name, "medicine");
        contentValues7.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + 10);
        contentValues7.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues8.put(SkillsDatabaseHelper.KEY_ID, 8);
        contentValues8.put(SkillsDatabaseHelper.KEY_Name, "science");
        contentValues8.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + 10);
        contentValues8.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        contentValues9.put(SkillsDatabaseHelper.KEY_ID, 9);
        contentValues9.put(SkillsDatabaseHelper.KEY_Name, "repair");
        contentValues9.put(SkillsDatabaseHelper.KEY_Value, mentality * 2 + watchfulness + dexterity + 10);
        contentValues9.put(SkillsDatabaseHelper.KEY_Is_Main, 0);

        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues1);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues2);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues3);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues4);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues5);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues6);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues7);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues8);
        skillsDatabase.insert(SkillsDatabaseHelper.Table_Skills, null, contentValues9);
    }
}
