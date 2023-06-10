package penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationDao;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;

public class OtherInformationRepository {
    private final OtherInformationDao dao;

    public OtherInformationRepository(Application application) {
        this.dao = Database.getDatabase(application).otherInformationDao();
    }

    public LiveData<List<OtherInformationItem>> getAllOtherInformation() {
        try {
            return new GetAllOtherInformationAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public LiveData<OtherInformationItem> getOtherInformationItem(byte ID) {
        try {
            return new GetOtherInformationItemAsyncTask(dao).execute(ID).get();
        } catch (ExecutionException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void add(OtherInformationItem item) {
        new AddInformationItemAsyncTask(dao).execute(item);
    }

    public void updateInformation(int newValue, byte ID) {
        new UpdateInformationAsyncTask(dao).execute(newValue, (int) ID);
    }

    private static class GetOtherInformationItemAsyncTask extends AsyncTask<Byte, Void, LiveData<OtherInformationItem>> {
        private final OtherInformationDao dao;

        public GetOtherInformationItemAsyncTask(OtherInformationDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<OtherInformationItem> doInBackground(Byte... bytes) {
            return dao.getOtherInformationItem(bytes[0]);
        }
    }

    private static class GetAllOtherInformationAsyncTask extends AsyncTask<Void, Void, LiveData<List<OtherInformationItem>>> {
        private final OtherInformationDao dao;

        public GetAllOtherInformationAsyncTask(OtherInformationDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<OtherInformationItem>> doInBackground(Void... voids) {
            return dao.getAllOtherInformation();
        }
    }

    private static class AddInformationItemAsyncTask extends AsyncTask<OtherInformationItem, Void, Void> {
        private final OtherInformationDao dao;

        public AddInformationItemAsyncTask(OtherInformationDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OtherInformationItem... otherInformationItems) {
            dao.addItem(otherInformationItems[0]);
            return null;
        }
    }

    private static class UpdateInformationAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final OtherInformationDao dao;

        public UpdateInformationAsyncTask(OtherInformationDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.updateInformation(integers[0], Byte.parseByte(String.valueOf(integers[1])));
            return null;
        }
    }
}
