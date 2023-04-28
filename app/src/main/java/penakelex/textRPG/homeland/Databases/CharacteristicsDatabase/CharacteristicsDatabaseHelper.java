package penakelex.textRPG.homeland.Databases.CharacteristicsDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.reactivex.rxjava3.annotations.Nullable;

public class CharacteristicsDatabaseHelper extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String Database_Name = "characteristicsdb",
            Table_Characteristics = "characteristics",
    KEY_ID = "_id", KEY_Name = "name", KEY_Value = "value";

    public CharacteristicsDatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(String.format("create table %s(%s integer primary key,%s text,%s integer)",
                Table_Characteristics, KEY_ID, KEY_Name, KEY_Value));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(String.format("drop table if exists %s", Table_Characteristics));
        onCreate(database);
    }
}
