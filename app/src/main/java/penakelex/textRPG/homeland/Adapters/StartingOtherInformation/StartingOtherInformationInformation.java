package penakelex.textRPG.homeland.Adapters.StartingOtherInformation;

public class StartingOtherInformationInformation {
    private String name;
    private int value;

    public StartingOtherInformationInformation(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
