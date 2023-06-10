package penakelex.textRPG.homeland.Dialogs.DialogHelper;

public class BooleanSP {
    private final String tag;
    private final boolean value;

    public BooleanSP(String tag, boolean value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public boolean getValue() {
        return value;
    }
}
