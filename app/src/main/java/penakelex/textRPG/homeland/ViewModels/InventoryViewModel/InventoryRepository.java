package penakelex.textRPG.homeland.ViewModels.InventoryViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryDao;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;

public class InventoryRepository {
    private final InventoryDao dao;

    public InventoryRepository(Application application) {
        this.dao = Database.getDatabase(application).inventoryDao();
    }

    public LiveData<List<InventoryItem>> getAllInventoryItems() {
        try {
            return new GetAllInventoriesItemsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(InventoryItem inventoryItem) {
        new AddInventoryItemAsyncTask(dao).execute(inventoryItem);
    }

    public void changeOwner(short ownerID, float newPrice, short ID) {
        new ChangeOwnerInventoryItemAsyncTask(dao).execute(ownerID, newPrice, ID);
    }

    public void throwAwayItem(InventoryItem item) {
        new DeleteInventoryItemAsyncTask(dao).execute(item);
    }

    public LiveData<List<InventoryItem>> getInventory(short owner) {
        try {
            return new GetInventoryAsyncTask(dao).execute(owner).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(dao).execute();
    }

    private static class AddInventoryItemAsyncTask extends AsyncTask<InventoryItem, Void, Void> {
        private final InventoryDao dao;

        public AddInventoryItemAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(InventoryItem... inventoryItems) {
            dao.insertItem(inventoryItems[0]);
            return null;
        }
    }

    private static class ChangeOwnerInventoryItemAsyncTask extends AsyncTask<Object, Void, Void> {
        private final InventoryDao dao;

        public ChangeOwnerInventoryItemAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.changeOwner((short) objects[0], (float) objects[1], (short) objects[2]);
            return null;
        }
    }

    private static class DeleteInventoryItemAsyncTask extends AsyncTask<InventoryItem, Void, Void> {
        private final InventoryDao dao;

        public DeleteInventoryItemAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(InventoryItem... inventoryItems) {
            dao.throwAwayItem(inventoryItems[0]);
            return null;
        }
    }

    private static class GetAllInventoriesItemsAsyncTask extends AsyncTask<Void, Void, LiveData<List<InventoryItem>>> {
        private final InventoryDao dao;

        public GetAllInventoriesItemsAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<InventoryItem>> doInBackground(Void... voids) {
            return dao.getAllInventories();
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final InventoryDao dao;

        public DeleteAllAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class GetInventoryAsyncTask extends AsyncTask<Short, Void, LiveData<List<InventoryItem>>> {
        private final InventoryDao dao;

        public GetInventoryAsyncTask(InventoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<InventoryItem>> doInBackground(Short... shorts) {
            return dao.getInventory(shorts[0]);
        }
    }
}
