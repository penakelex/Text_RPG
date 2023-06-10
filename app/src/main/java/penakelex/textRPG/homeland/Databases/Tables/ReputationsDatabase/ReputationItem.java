package penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reputation")
public class ReputationItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private short ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "reputation")
    private byte reputation;

    public ReputationItem(int name) {
        this.name = name;
        this.reputation = 0;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public byte getReputation() {
        return reputation;
    }

    public void setReputation(byte reputation) {
        this.reputation = reputation;
    }
}
