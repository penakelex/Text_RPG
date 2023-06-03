package penakelex.textRPG.homeland.Databases.TalentsDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TalentsDatabaseHelper extends SQLiteOpenHelper {
    public final static int Database_Version = 1;
    public final static String Database_Name = "talentsdb", Table_Talents = "talents",
            KEY_ID = "_id", KEY_Name = "name", KEY_Having = "availability";

    public TalentsDatabaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(String.format("create table %s(%s integer primary key,%s text,%s integer)",
                Table_Talents, KEY_ID, KEY_Name, KEY_Having));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + Table_Talents);
        onCreate(database);
    }
}
