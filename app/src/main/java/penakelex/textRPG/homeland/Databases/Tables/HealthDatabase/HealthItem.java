package penakelex.textRPG.homeland.Databases.Tables.HealthDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "health_status")
public class HealthItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "value")
    private short value;

    @ColumnInfo(name = "base_value")
    private short baseValue;

    public HealthItem(int name) {
        this.name = name;
        this.value = 1;
        this.baseValue = 1;
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

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(short baseValue) {
        this.baseValue = baseValue;
    }
}
