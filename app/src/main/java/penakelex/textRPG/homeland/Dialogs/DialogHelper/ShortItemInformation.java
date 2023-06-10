package penakelex.textRPG.homeland.Dialogs.DialogHelper;

public class ShortItemInformation {
    private final short ID, owner;

    public ShortItemInformation(short ID, short owner) {
        this.ID = ID;
        this.owner = owner;
    }

    public ShortItemInformation(short ID) {
        this.ID = ID;
        this.owner = 1;
    }

    public short getID() {
        return ID;
    }

    public short getOwner() {
        return owner;
    }
}
