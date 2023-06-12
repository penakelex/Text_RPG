package penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InventoryDao {
    @Query("SELECT * FROM inventory")
    List<InventoryItem> getAllInventories();

    @Insert
    void insertItem(InventoryItem item);

    @Delete
    void throwAwayItem(InventoryItem inventoryItem);

    @Query("UPDATE inventory SET owner_id=:ownerID, price=:newPrice WHERE primary_id=:ID")
    void changeOwner(short ownerID, float newPrice, short ID);

    @Query("DELETE FROM inventory")
    void deleteAll();

    @Query("SELECT * FROM inventory WHERE owner_id=:owner")
    List<InventoryItem> getInventory(short owner);
}
