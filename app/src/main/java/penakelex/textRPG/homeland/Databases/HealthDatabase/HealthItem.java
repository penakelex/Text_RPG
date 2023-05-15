package penakelex.textRPG.homeland.Databases.HealthDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "health_status")
public class HealthItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "value")
    private short value;

    @ColumnInfo(name = "base_value")
    private short baseValue;

    public HealthItem(String name, short baseValue) {
        this.name = name;
        this.value = baseValue;
        this.baseValue = baseValue;
    }

    public byte getID() {
        return ID;
    }

    public void setID(byte ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
