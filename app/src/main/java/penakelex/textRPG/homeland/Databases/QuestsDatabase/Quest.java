package penakelex.textRPG.homeland.Databases.QuestsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "quests")
public class Quest {
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "stage")
    private short stage;

    public Quest(String name, short stage) {
        this.name = name;
        this.stage = stage;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public short getStage() {
        return stage;
    }

    public void setStage(short stage) {
        this.stage = stage;
    }
}
