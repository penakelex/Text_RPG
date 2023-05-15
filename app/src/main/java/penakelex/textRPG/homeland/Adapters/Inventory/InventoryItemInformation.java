package penakelex.textRPG.homeland.Adapters.Inventory;

public class InventoryItemInformation {
    private int ID;
    private long primaryID;

    public InventoryItemInformation(int ID, long primaryID) {
        this.ID = ID;
        this.primaryID = primaryID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(long primaryID) {
        this.primaryID = primaryID;
    }
}
