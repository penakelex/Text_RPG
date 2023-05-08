package penakelex.textRPG.homeland.Adapters.StartingSkills;

public class StartingSkillInformation {
    private String name;
    private boolean isMain;

    public StartingSkillInformation(String name, boolean isMain) {
        this.name = name;
        this.isMain = isMain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
