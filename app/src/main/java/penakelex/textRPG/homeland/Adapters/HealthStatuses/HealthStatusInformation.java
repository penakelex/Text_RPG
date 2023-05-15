package penakelex.textRPG.homeland.Adapters.HealthStatuses;

public class HealthStatusInformation {
    private String name;
    private short value;
    private short baseValue;

    public HealthStatusInformation(String name, short value, short baseValue) {
        this.name = name;
        this.value = value;
        this.baseValue = baseValue;
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
