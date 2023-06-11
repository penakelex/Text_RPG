package penakelex.textRPG.homeland.ViewModels.InventoryViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;

public class InventoryViewModel extends ViewModel {
    private InventoryRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new InventoryRepository(application);
    }

    public List<InventoryItem> getAllInventoriesItems() {
        return repository.getAllInventoryItems();
    }

    public void add(InventoryItem item) {
        repository.add(item);
    }

    public void changeOwner(short ownerID, float newPrice, short ID) {
        repository.changeOwner(ownerID, newPrice, ID);
    }

    public void throwAwayItem(InventoryItem item) {
        repository.throwAwayItem(item);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<InventoryItem> getInventory(short ID) {
        return repository.getInventory(ID);
    }
}
