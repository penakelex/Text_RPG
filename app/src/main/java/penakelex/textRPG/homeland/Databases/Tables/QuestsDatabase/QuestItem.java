package penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quests")
public class QuestItem {
    @ColumnInfo(name = "name")
    private int name;
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private short ID;
    @ColumnInfo(name = "stage")
    private short stages;

    public QuestItem(int name) {
        this.name = name;
        this.stages = 1;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public short getStages() {
        return stages;
    }

    public void setStages(short stages) {
        this.stages = stages;
    }
}
