package penakelex.textRPG.homeland.Databases.ReputationsDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ReputationItem.class}, version = 1)
public abstract class ReputationDatabase extends RoomDatabase {
    private static final String Database_Name = "reputation.db";
    private static ReputationDatabase reputationDatabase;

    public static synchronized ReputationDatabase getDatabase(Context context) {
        if (reputationDatabase == null) {
            reputationDatabase = Room.databaseBuilder(context.getApplicationContext(), ReputationDatabase.class, Database_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return reputationDatabase;
    }

    public abstract ReputationDao reputationDao();
}
