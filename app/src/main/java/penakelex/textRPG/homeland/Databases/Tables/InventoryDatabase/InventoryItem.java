package penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory")
public class InventoryItem {
    @ColumnInfo(name = "id")
    private short id;
    @ColumnInfo(name = "primary_id")
    @PrimaryKey(autoGenerate = true)
    private int primaryID;
    @ColumnInfo(name = "owner_id")
    private short ownerID;
    @ColumnInfo(name = "price")
    private float price;

    public InventoryItem(short id, short ownerID, float price) {
        this.id = id;
        this.ownerID = ownerID;
        this.price = price;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public int getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(int primaryID) {
        this.primaryID = primaryID;
    }

    public short getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(short ownerID) {
        this.ownerID = ownerID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
