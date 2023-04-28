package penakelex.textRPG.homeland.Databases.OtherInfromationDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;

public class OtherInformationDatabase {

    public static int getValue(SQLiteDatabase database, int ID) {
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int idColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_ID),
                valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value),
                value = 0;
        cursor.moveToFirst();
        do {
            if (cursor.getInt(idColumnIndex) == ID) {
                value = cursor.getInt(valueColumnIndex);
                break;
            }
        } while (cursor.moveToNext());
        cursor.close();
        return value;
    }

    public static int[] getValues(SQLiteDatabase database) {
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value);
        ArrayList<Integer> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        do {
            arrayList.add(cursor.getInt(valueColumnIndex));
        } while (cursor.moveToNext());
        cursor.close();
        int[] values = new int[arrayList.size()];
        for (int i = 0; i < values.length; i++) values[i] = arrayList.get(i);
        return values;
    }

    public static void settingStartingValuesInInformationDatabase(SQLiteDatabase infoDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        infoDatabase.delete(OtherInformationDatabaseHelper.Table_Other_Info, null, null);
        int[] characteristics = CharacteristicsDatabase.getCharacteristicsValues(characteristicsDatabase);
        int[] skills = SkillsDatabase.getSkillValues(skillsDatabase);
        ContentValues contentValues1 = new ContentValues(), contentValues2 = new ContentValues(),
                contentValues3 = new ContentValues(), contentValues4 = new ContentValues(),
                contentValues5 = new ContentValues(), contentValues6 = new ContentValues(),
                contentValues7 = new ContentValues();

        contentValues1.put(OtherInformationDatabaseHelper.KEY_ID, 1);
        contentValues2.put(OtherInformationDatabaseHelper.KEY_ID, 2);
        contentValues3.put(OtherInformationDatabaseHelper.KEY_ID, 3);
        contentValues4.put(OtherInformationDatabaseHelper.KEY_ID, 4);
        contentValues5.put(OtherInformationDatabaseHelper.KEY_ID, 5);
        contentValues6.put(OtherInformationDatabaseHelper.KEY_ID, 6);
        contentValues7.put(OtherInformationDatabaseHelper.KEY_ID, 7);

        contentValues1.put(OtherInformationDatabaseHelper.KEY_Name, "armor_class");
        contentValues2.put(OtherInformationDatabaseHelper.KEY_Name, "ap");
        contentValues3.put(OtherInformationDatabaseHelper.KEY_Name, "load_capacity");
        contentValues4.put(OtherInformationDatabaseHelper.KEY_Name, "carry_volume");
        contentValues5.put(OtherInformationDatabaseHelper.KEY_Name, "melee_damage");
        contentValues6.put(OtherInformationDatabaseHelper.KEY_Name, "critical_hit");
        contentValues7.put(OtherInformationDatabaseHelper.KEY_Name, "health_points");

        contentValues1.put(OtherInformationDatabaseHelper.KEY_Value, 0);
        contentValues2.put(OtherInformationDatabaseHelper.KEY_Value, Math.max(characteristics[2] + characteristics[1], 5));
        contentValues3.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 10 + characteristics[2] * 3 + 20);
        contentValues4.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 2000 + characteristics[2] * 1000 + 10000); // В кубических сантиметрах
        contentValues5.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] + characteristics[2] / 2 + skills[2] / 20);
        contentValues6.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[4] * 2 + characteristics[2] + characteristics[5] / 2 + skills[6] / 25);
        contentValues7.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[1] * 2 + skills[5] / 2);

        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues1);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues2);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues3);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues4);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues5);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues6);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues7);
    }
}
