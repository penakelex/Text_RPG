package penakelex.textRPG.homeland.Adapters.TradingAdapter;

public class TradingInformation {
    private String name;
    private float value;
    private long primaryID;

    public TradingInformation(String name, float value, long primaryID) {
        this.name = name;
        this.value = value;
        this.primaryID = primaryID;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public long getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(long primaryID) {
        this.primaryID = primaryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
