package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InventoryDao {
    @Query("SELECT * FROM inventory WHERE owner_id=:ownersID")
    List<InventoryItem> getInventory(int ownersID);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(InventoryItem item);

    @Delete
    void throwAwayItem(InventoryItem item);

    @Query("UPDATE inventory SET owner_id=:ownerID, price=:newPrice WHERE primary_id=:ID")
    void changeOwner(int ownerID, int newPrice, long ID);

    @Query("UPDATE inventory SET count=:newCount WHERE primary_id=:ID")
    void updateCount(long newCount, long ID);

    @Query("DELETE FROM inventory")
    void deleteAll();
}
