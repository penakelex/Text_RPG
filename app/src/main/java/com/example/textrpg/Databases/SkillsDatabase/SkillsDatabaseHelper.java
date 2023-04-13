package com.example.textrpg.Databases.SkillsDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.room.Database;

public class SkillsDatabaseHelper extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String Database_Name = "skillsdb", Table_Skills = "skills",
            KEY_ID = "_id", KEY_Name = "name", KEY_Value = "value", KEY_Is_Main = "is_main";

    public SkillsDatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(String.format("create table %s(%s integer primary key,%s text,%s integer,%s integer)",
                Table_Skills, KEY_ID, KEY_Name, KEY_Value, KEY_Is_Main));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table_Skills);
        onCreate(db);
    }
}
