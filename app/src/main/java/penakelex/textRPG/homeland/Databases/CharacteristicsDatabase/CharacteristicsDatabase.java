package penakelex.textRPG.homeland.Databases.CharacteristicsDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Constants;

public class CharacteristicsDatabase {
    public static int[] getCharacteristicsValues(SQLiteDatabase database) {
        int valueColumnIndex;
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            list.add(cursor.getInt(valueColumnIndex));
        } while (cursor.moveToNext());
        cursor.close();
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }

    public static int getNewValue(SQLiteDatabase database, int ID) {
        Cursor afterCursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        int newIdColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                newValueColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                newValue = 0;
        afterCursor.moveToFirst();
        do {
            if (afterCursor.getInt(newIdColumnIndex) == ID) {
                newValue = afterCursor.getInt(newValueColumnIndex);
                break;
            }
        } while (afterCursor.moveToNext());
        afterCursor.close();
        return newValue;
    }

    @SuppressLint("DefaultLocale")
    public static int raiseCharacteristic(SQLiteDatabase database, SharedPreferences sharedPreferences, int ID) {
        if (ID != 0) {
            Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
            int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                    valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                    value = 0;
            cursor.moveToFirst();
            do {
                if (cursor.getInt(idColumnIndex) == ID) {
                    value = cursor.getInt(valueColumnIndex);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (value + 1 <= 5 && sharedPreferences.getInt(Constants.Characteristics_Points, 2) - 1 >= 0) {
                database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value + 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        ID));
                sharedPreferences.edit().putInt(Constants.Characteristics_Points, sharedPreferences.getInt(Constants.Characteristics_Points, 2) - 1).apply();
                return 1;
            } else {
                if (value + 1 > 5) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else return 0;
    }

    @SuppressLint("DefaultLocale")
    public static int downgradeCharacteristic(SQLiteDatabase database, SharedPreferences sharedPreferences, int ID) {
        if (ID != 0) {
            Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
            cursor.moveToFirst();
            int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                    valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                    value = 0;
            do {
                if (cursor.getInt(idColumnIndex) == ID) {
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
                        ID));
                sharedPreferences.edit().putInt(Constants.Characteristics_Points, sharedPreferences.getInt(Constants.Characteristics_Points, 2) + 1).apply();
                return 1;
            } else return 2;
        } else  return 3;
    }

    public static void settingStartValuesInDatabase(SQLiteDatabase database) {
        database.delete(CharacteristicsDatabaseHelper.Table_Characteristics, null, null);

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
}
