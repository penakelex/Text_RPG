package penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "talents")
public class TalentItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "is_having")
    private boolean isHaving;

    public TalentItem(int name) {
        this.name = name;
        this.isHaving = false;
    }

    public byte getID() {
        return ID;
    }

    public void setID(byte ID) {
        this.ID = ID;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isHaving() {
        return isHaving;
    }

    public void setHaving(boolean having) {
        isHaving = having;
    }
}
