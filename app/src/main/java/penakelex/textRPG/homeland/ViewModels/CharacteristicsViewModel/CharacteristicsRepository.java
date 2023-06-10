package penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicsDao;
import penakelex.textRPG.homeland.Databases.Database.Database;

public class CharacteristicsRepository {
    private final CharacteristicsDao dao;

    public CharacteristicsRepository(Application application) {
        this.dao = Database.getDatabase(application).characteristicsDao();
    }

    public LiveData<List<CharacteristicItem>> getAllCharacteristics() {
        try {
            return new GetAllCharacteristicsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(CharacteristicItem item) {
        new AddCharacteristicAsyncTask(dao).execute(item);
    }

    public void update(byte newValue, byte ID) {
        new UpdateCharacteristicAsyncTask(dao).execute(newValue, ID);
    }

    public LiveData<CharacteristicItem> getCharacteristic(byte ID) {
        try {
            return new GetCharacteristicAsyncTask(dao).execute(ID).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class AddCharacteristicAsyncTask extends AsyncTask<CharacteristicItem, Void, Void> {
        private final CharacteristicsDao dao;

        public AddCharacteristicAsyncTask(CharacteristicsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CharacteristicItem... characteristicItems) {
            dao.addCharacteristic(characteristicItems[0]);
            return null;
        }
    }

    private static class UpdateCharacteristicAsyncTask extends AsyncTask<Byte, Void, Void> {
        private final CharacteristicsDao dao;

        public UpdateCharacteristicAsyncTask(CharacteristicsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Byte... bytes) {
            dao.updateValue(bytes[0], bytes[1]);
            return null;
        }
    }

    private static class GetAllCharacteristicsAsyncTask extends AsyncTask<Void, Void, LiveData<List<CharacteristicItem>>> {
        private final CharacteristicsDao dao;

        public GetAllCharacteristicsAsyncTask(CharacteristicsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<CharacteristicItem>> doInBackground(Void... voids) {
            return dao.getAllCharacteristics();
        }
    }

    private static class GetCharacteristicAsyncTask extends AsyncTask<Byte, Void, LiveData<CharacteristicItem>> {
        private final CharacteristicsDao dao;

        public GetCharacteristicAsyncTask(CharacteristicsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<CharacteristicItem> doInBackground(Byte... bytes) {
            return dao.getCharacteristic(bytes[0]);
        }
    }
}
