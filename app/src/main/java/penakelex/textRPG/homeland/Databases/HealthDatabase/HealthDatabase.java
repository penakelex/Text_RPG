package penakelex.textRPG.homeland.Databases.HealthDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HealthItem.class}, version = 1)
public abstract class HealthDatabase extends RoomDatabase {
    private static final String Database_Name = "health_status.db";
    private static HealthDatabase healthDatabase;

    public static synchronized HealthDatabase getDatabase(Context context) {
        if (healthDatabase == null) {
            healthDatabase = Room.databaseBuilder(context.getApplicationContext(), HealthDatabase.class, Database_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return healthDatabase;
    }

    public abstract HealthDao healthDao();
}
