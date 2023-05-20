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
    private short stages;

    public Quest(String name) {
        this.name = name;
        this.stages = 1;

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

    public short getStages() {
        return stages;
    }

    public void setStages(short stages) {
        this.stages = stages;
    }
}
