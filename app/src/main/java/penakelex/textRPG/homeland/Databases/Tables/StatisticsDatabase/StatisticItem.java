package penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistics")
public class StatisticItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "count")
    private short count;

    public StatisticItem(int name) {
        this.count = 0;
        this.name = name;
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

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }
}
