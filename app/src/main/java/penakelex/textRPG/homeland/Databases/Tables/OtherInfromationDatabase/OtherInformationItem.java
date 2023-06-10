package penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "other_information")
public class OtherInformationItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "value")
    private int value;

    public OtherInformationItem(int name) {
        this.name = name;
        this.value = 0;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
