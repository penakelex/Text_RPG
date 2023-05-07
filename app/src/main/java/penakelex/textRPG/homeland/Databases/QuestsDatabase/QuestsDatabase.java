package penakelex.textRPG.homeland.Databases.QuestsDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Quest.class}, exportSchema = false, version = 1)
public abstract class QuestsDatabase extends RoomDatabase {
    private static final String Database_Name = "quests.db";
    private static QuestsDatabase database;

    public static synchronized QuestsDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), QuestsDatabase.class,
                    Database_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract QuestsDao questsDao();
}
