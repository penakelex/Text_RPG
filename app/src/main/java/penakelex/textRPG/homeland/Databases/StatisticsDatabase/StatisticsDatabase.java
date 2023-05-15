package penakelex.textRPG.homeland.Databases.StatisticsDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {StatisticItem.class}, version = 1)
public abstract class StatisticsDatabase extends RoomDatabase {
    private static final String Database_Name = "statistics.db";
    private static StatisticsDatabase database;
    public static synchronized StatisticsDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), StatisticsDatabase.class, Database_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract StatisticsDao statisticsDao();
}
