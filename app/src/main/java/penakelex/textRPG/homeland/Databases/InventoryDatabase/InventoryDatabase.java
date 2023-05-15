package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InventoryItem.class}, exportSchema = false, version = 4)
public abstract class InventoryDatabase extends RoomDatabase {
    private static final String Database_Name = "inventory.db";
    private static InventoryDatabase database;
    public static synchronized InventoryDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), InventoryDatabase.class,
                    Database_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract InventoryDao inventoryDao();
}
