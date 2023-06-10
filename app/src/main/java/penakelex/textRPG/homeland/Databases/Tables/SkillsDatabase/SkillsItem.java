package penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skills")
public class SkillsItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private byte ID;

    @ColumnInfo(name = "name")
    private int name;

    @ColumnInfo(name = "value")
    private byte value;

    @ColumnInfo(name = "is_main")
    private byte isMain;

    public SkillsItem(int name) {
        this.name = name;
        this.value = 0;
        this.isMain = 0;
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

    public boolean isMain() {
        return isMain == 1;
    }

    public byte getIsMain() {
        return isMain;
    }

    public void setIsMain(byte isMain) {
        this.isMain = isMain;
    }
}
