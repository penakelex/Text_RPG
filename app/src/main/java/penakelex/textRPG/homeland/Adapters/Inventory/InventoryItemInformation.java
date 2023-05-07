package penakelex.textRPG.homeland.Adapters.Inventory;

public class InventoryItemInformation {
    private String itemName;
    private String type;
    private long ID;

    public InventoryItemInformation(String itemName, String type, long ID) {
        this.itemName = itemName;
        this.type = type;
        this.ID = ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
