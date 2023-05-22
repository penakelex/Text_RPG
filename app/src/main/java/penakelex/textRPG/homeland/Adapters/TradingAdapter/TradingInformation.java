package penakelex.textRPG.homeland.Adapters.TradingAdapter;

public class TradingInformation {
    private String name;
    private float value;

    public TradingInformation(String name, float value) {
        this.name = name;
        this.value = value;
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
