package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory")
public class InventoryItem {
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "primary_id")
    @PrimaryKey(autoGenerate = true)
    private long primaryID;
    @ColumnInfo(name = "owner_id")
    private int ownerID;
    @ColumnInfo(name = "price")
    private float price;

    public InventoryItem(int id, int ownerID, float price) {
        this.id = id;
        this.ownerID = ownerID;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(long primaryID) {
        this.primaryID = primaryID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
