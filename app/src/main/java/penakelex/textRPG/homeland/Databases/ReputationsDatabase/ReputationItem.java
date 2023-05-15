package penakelex.textRPG.homeland.Databases.ReputationsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity(tableName = "reputation")
public class ReputationItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private short ID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "reputation")
    private byte reputation;

    public ReputationItem(String name) {
        this.name = name;
        this.reputation = 1;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getReputation() {
        return reputation;
    }

    public void setReputation(byte reputation) {
        this.reputation = reputation;
    }
}
