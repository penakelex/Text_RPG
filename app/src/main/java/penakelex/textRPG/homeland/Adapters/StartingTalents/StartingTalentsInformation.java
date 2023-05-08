package penakelex.textRPG.homeland.Adapters.StartingTalents;

public class StartingTalentsInformation {
    private String name;
    private boolean isHaving;

    public StartingTalentsInformation(String name, boolean isHaving) {
        this.name = name;
        this.isHaving = isHaving;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHaving() {
        return isHaving;
    }

    public void setHaving(boolean having) {
        isHaving = having;
    }
}
