package penakelex.textRPG.homeland.Databases.MapDatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapDataBase extends SQLiteOpenHelper {
    private static final int Database_Version = 1;
    public static final String Database_Name = "locationDb", Table_Locations = "locations",

    Key_ID = "id", Key_Attendance = "attendance", Key_Settlement = "settlement";

    public MapDataBase(Context context) {
        super(context, Database_Name, null, Database_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(String.format("create table %s(%s integer primary key,%s numeric,%s numeric)", Table_Locations, Key_ID, Key_Attendance, Key_Settlement));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
