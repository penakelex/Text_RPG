package penakelex.textRPG.homeland.Adapters.Quests;

import java.util.ArrayList;

public class QuestInformation {
    private int ID;
    private short stage;
    private String name;

    public QuestInformation(int ID, short stage, String name) {
        this.ID = ID;
        this.stage = stage;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
