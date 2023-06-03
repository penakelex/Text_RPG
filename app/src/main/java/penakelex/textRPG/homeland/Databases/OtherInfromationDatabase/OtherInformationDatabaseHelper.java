package penakelex.textRPG.homeland.Databases.OtherInfromationDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class OtherInformationDatabaseHelper extends SQLiteOpenHelper {
    public final static int Database_Version = 1;
    public final static String Database_Name = "otherinfodb", Table_Other_Info = "other_info",
            KEY_ID = "_id", KEY_Name = "name", KEY_Value = "value";

    public OtherInformationDatabaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(String.format("create table %s(%s integer primary key,%s text,%s integer)",
                Table_Other_Info, KEY_ID, KEY_Name, KEY_Value));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + Table_Other_Info);
        onCreate(database);
    }
}
