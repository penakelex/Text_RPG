package penakelex.textRPG.homeland.Databases.CharacteristicsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "characteristics")
public class CharacteristicItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "value")
    private byte value;

    public CharacteristicItem(int name, byte value) {
        this.name = name;
        this.value = value;
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

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
