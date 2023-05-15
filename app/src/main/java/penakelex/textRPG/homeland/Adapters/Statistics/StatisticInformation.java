package penakelex.textRPG.homeland.Adapters.Statistics;

public class StatisticInformation {
    private String name;
    private short count;

    public StatisticInformation(String name, short count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }
}
