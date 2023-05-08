package penakelex.textRPG.homeland.Databases.TalentsDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Adapters.StartingTalents.StartingTalentsInformation;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Adapters.Capabilities.CapabilitiesInformation;

public class TalentsDatabase {
    public static boolean[] getIsHaving(SQLiteDatabase database) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        boolean[] isHaving;
        Cursor cursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        cursor.moveToFirst();
        do {
            arrayList.add(cursor.getInt(isHavingColumnIndex) == 1);
        } while (cursor.moveToNext());
        cursor.close();
        isHaving = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) isHaving[i] = arrayList.get(i);
        return isHaving;
    }

    public static ArrayList<StartingTalentsInformation> getStartingTalentsInformation(SQLiteDatabase database) {
        ArrayList<StartingTalentsInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Name),
                isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        cursor.moveToFirst();
        do {
            arrayList.add(new StartingTalentsInformation(cursor.getString(nameColumnIndex), cursor.getInt(isHavingColumnIndex) == 1));
        } while (cursor.moveToNext());
        cursor.close();
        return arrayList;
    }

    public static ArrayList<CapabilitiesInformation> getHavingTalents(Context context) {
        Cursor cursor = new TalentsDatabaseHelper(context).getReadableDatabase().query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having),
                idColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_ID), ID, count = 0;
        ArrayList<CapabilitiesInformation> information = new ArrayList<>();
        cursor.moveToFirst();
        do {
            if (cursor.getInt(isHavingColumnIndex) == 1) {
                ID = cursor.getInt(idColumnIndex);
                switch (ID) {
                    case 1:
                        information.add(new CapabilitiesInformation("Певец", ID));
                        break;
                    case 2:
                        information.add(new CapabilitiesInformation("Бугай", ID));
                        break;
                    case 3:
                        information.add(new CapabilitiesInformation("Сильный удар", ID));
                        break;
                    case 4:
                        information.add(new CapabilitiesInformation("Опытный", ID));
                        break;
                    case 5:
                        information.add(new CapabilitiesInformation("Натренированный", ID));
                        break;
                    case 6:
                        information.add(new CapabilitiesInformation("Тяжеловес", ID));
                        break;
                    case 7:
                        information.add(new CapabilitiesInformation("Добрый малый", ID));
                        break;
                }
                count++;
            }
        } while (cursor.moveToNext() && count < 2);
        cursor.close();
        return information;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingKindOne(SQLiteDatabase talentsDatabase, SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase) {
        if (isHaving(7, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    7));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    7));
        }
        SkillsDatabase.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
        return true;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingHeavyweight(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase) {
        int loadCapacity = OtherInformationDatabase.getValue(infoDatabase, 3);
        Log.d("weight", String.valueOf(OtherInformationDatabase.getValue(infoDatabase, 3)));
        if (isHaving(6, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    6));
            infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    OtherInformationDatabaseHelper.Table_Other_Info,
                    OtherInformationDatabaseHelper.KEY_Value,
                    loadCapacity - 20,
                    OtherInformationDatabaseHelper.KEY_ID,
                    3));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    6));
            infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    OtherInformationDatabaseHelper.Table_Other_Info,
                    OtherInformationDatabaseHelper.KEY_Value,
                    loadCapacity + 20,
                    OtherInformationDatabaseHelper.KEY_ID,
                    3));
        }
        Log.d("weight", String.valueOf(OtherInformationDatabase.getValue(infoDatabase, 3)));
        return loadCapacity >= 20;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingTrained(SQLiteDatabase talentsDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        int[] characteristics = CharacteristicsDatabase.getCharacteristicsValues(characteristicsDatabase);
        boolean isAllGood = true;
        if (isHaving(5, talentsDatabase)) {
            for (int characteristic : characteristics) {
                if (characteristic - 1 < 0) {
                    isAllGood = false;
                    break;
                }
            }
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        5));
                for (int i = 0; i < characteristics.length; i++) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            characteristics[i] - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            i + 1));
                }
                SkillsDatabase.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
            }
        } else {
            for (int characteristic : characteristics) {
                if (characteristic + 1 > 5) {
                    isAllGood = false;
                    break;
                }
            }
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        5));
                for (int i = 0; i < characteristics.length; i++) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            characteristics[i] + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            i + 1));
                }
                SkillsDatabase.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
            }
        }
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingExperienced(SQLiteDatabase talentsDatabase, SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase) {
        if (isHaving(4, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    4));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    4));
        }
        SkillsDatabase.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
        return true;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingStrongKick(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase) {
        int meleeDamage = OtherInformationDatabase.getValue(infoDatabase, 5),
                criticalHit = OtherInformationDatabase.getValue(infoDatabase, 6);
        Log.d("crit and meleedmg", String.format("%d, %d", meleeDamage, criticalHit));
        boolean isAllGood;
        if (isHaving(3, talentsDatabase)) {
            isAllGood = meleeDamage - 1 >= 0 && criticalHit + 5 <= 100;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        3));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        meleeDamage - 1,
                        OtherInformationDatabaseHelper.KEY_ID,
                        5));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        criticalHit + 5,
                        OtherInformationDatabaseHelper.KEY_ID,
                        6));
            }
        } else {
            isAllGood = criticalHit - 5 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        3));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        meleeDamage + 1,
                        OtherInformationDatabaseHelper.KEY_ID,
                        5));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        criticalHit - 5,
                        OtherInformationDatabaseHelper.KEY_ID,
                        6));
            }
        }
        Log.d("crit and meleedmg", String.format("%d, %d", OtherInformationDatabase.getValue(infoDatabase, 5), OtherInformationDatabase.getValue(infoDatabase, 6)));
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingBull(SQLiteDatabase talentsDatabase, SQLiteDatabase
            characteristicsDatabase, SQLiteDatabase infoDatabase) {
        int strength = CharacteristicsDatabase.getNewValue(characteristicsDatabase, 1),
                physique = CharacteristicsDatabase.getNewValue(characteristicsDatabase, 2),
                ap = OtherInformationDatabase.getValue(infoDatabase, 2);
        Log.d("value of ap, strength, physique", String.format("%d, %d,%d",
                OtherInformationDatabase.getValue(infoDatabase, 2),
                strength, physique));
        boolean isAllGood;
        if (isHaving(2, talentsDatabase)) {
            isAllGood = strength - 1 >= 0 && physique - 1 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        2));
                if (strength >= 1) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            strength - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            1));
                }
                if (physique >= 1) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            physique - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            2));
                }
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        ap + 2,
                        OtherInformationDatabaseHelper.KEY_ID,
                        2));
            }
        } else {
            isAllGood = strength + 1 <= 5 && physique + 1 <= 5;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        2));
                if (strength <= 4) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            strength + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            1));
                }
                if (physique <= 4) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            physique + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            2));
                }
                if (ap >= 2) {
                    infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            OtherInformationDatabaseHelper.Table_Other_Info,
                            OtherInformationDatabaseHelper.KEY_Value,
                            ap - 2,
                            OtherInformationDatabaseHelper.KEY_ID,
                            2));
                }
            }
        }
        Log.d("value of ap, strength, physique", String.format("%d, %d,%d",
                OtherInformationDatabase.getValue(infoDatabase, 2),
                CharacteristicsDatabase.getNewValue(characteristicsDatabase, 1),
                CharacteristicsDatabase.getNewValue(characteristicsDatabase, 2)));
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingSinger(SQLiteDatabase talentsDatabase, SQLiteDatabase
            characteristicsDatabase) {
        int value = CharacteristicsDatabase.getNewValue(characteristicsDatabase, 7);
        boolean isAllGood;
        if (isHaving(1, talentsDatabase)) {
            isAllGood = value - 1 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        1));
                characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value - 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        7));
            }
        } else {
            isAllGood = value + 1 <= 5;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        1));

                characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value + 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        7));
            }
        }
        return isAllGood;
    }

    public static void settingStartingValuesInDatabase(SQLiteDatabase database, String[] names) {
        database.delete(TalentsDatabaseHelper.Table_Talents, null, null);
        ContentValues contentValues1 = new ContentValues(),
                contentValues2 = new ContentValues(), contentValues3 = new ContentValues(),
                contentValues4 = new ContentValues(), contentValues5 = new ContentValues(),
                contentValues6 = new ContentValues(), contentValues7 = new ContentValues();

        contentValues1.put(TalentsDatabaseHelper.KEY_ID, 1);
        contentValues2.put(TalentsDatabaseHelper.KEY_ID, 2);
        contentValues3.put(TalentsDatabaseHelper.KEY_ID, 3);
        contentValues4.put(TalentsDatabaseHelper.KEY_ID, 4);
        contentValues5.put(TalentsDatabaseHelper.KEY_ID, 5);
        contentValues6.put(TalentsDatabaseHelper.KEY_ID, 6);
        contentValues7.put(TalentsDatabaseHelper.KEY_ID, 7);

        contentValues1.put(TalentsDatabaseHelper.KEY_Name, names[0]);
        contentValues2.put(TalentsDatabaseHelper.KEY_Name, names[1]);
        contentValues3.put(TalentsDatabaseHelper.KEY_Name, names[2]);
        contentValues4.put(TalentsDatabaseHelper.KEY_Name, names[3]);
        contentValues5.put(TalentsDatabaseHelper.KEY_Name, names[4]);
        contentValues6.put(TalentsDatabaseHelper.KEY_Name, names[5]);
        contentValues7.put(TalentsDatabaseHelper.KEY_Name, names[6]);

        contentValues1.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues2.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues3.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues4.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues5.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues6.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues7.put(TalentsDatabaseHelper.KEY_Having, 0);

        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues1);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues2);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues3);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues4);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues5);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues6);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues7);
    }

    public static boolean isHaving(int ID, SQLiteDatabase database) {
        Cursor havingTalentCursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
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
}
