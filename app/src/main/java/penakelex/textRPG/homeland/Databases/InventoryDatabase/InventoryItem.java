package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory")
public class InventoryItem {
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "primary_id")
    @PrimaryKey(autoGenerate = true)
    private long primaryID;

    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "owner_id")
    private int ownerID;
    @ColumnInfo(name = "is_stackable")
    private boolean is_stackable;
    @ColumnInfo(name = "count")
    private long count;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "is_for_quest")
    private boolean isForQuest;

    public InventoryItem(String name, int id, String type, int ownerID, boolean is_stackable, long count, int price, boolean isForQuest) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.ownerID = ownerID;
        this.is_stackable = is_stackable;
        this.count = count;
        this.price = price;
        this.isForQuest = isForQuest;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public boolean isIs_stackable() {
        return is_stackable;
    }

    public long getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setIs_stackable(boolean is_stackable) {
        this.is_stackable = is_stackable;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(long primaryID) {
        this.primaryID = primaryID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isForQuest() {
        return isForQuest;
    }

    public void setForQuest(boolean forQuest) {
        isForQuest = forQuest;
    }
}
