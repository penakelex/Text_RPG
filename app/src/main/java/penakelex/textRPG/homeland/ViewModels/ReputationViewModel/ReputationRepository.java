package penakelex.textRPG.homeland.ViewModels.ReputationViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationDao;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;

public class ReputationRepository {
    private final ReputationDao dao;

    public ReputationRepository(Application application) {
        this.dao = Database.getDatabase(application).reputationDao();
    }

    public LiveData<List<ReputationItem>> getAllReputations() {
        try {
            return new GetAllReputationsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(ReputationItem item) {
        new AddReputationAsyncTask(dao).execute(item);
    }

    public LiveData<ReputationItem> getReputation(short ID) {
        try {
            return new GetReputationAsyncTask(dao).execute(ID).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(byte newReputation, short ID) {
        new UpdateReputationAsyncTask(dao).execute((short) newReputation, ID);
    }

    private static class GetAllReputationsAsyncTask extends AsyncTask<Void, Void, LiveData<List<ReputationItem>>> {
        private final ReputationDao dao;

        public GetAllReputationsAsyncTask(ReputationDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<ReputationItem>> doInBackground(Void... voids) {
            return dao.getAllReputation();
        }
    }

    private static class AddReputationAsyncTask extends AsyncTask<ReputationItem, Void, Void> {
        private final ReputationDao dao;

        public AddReputationAsyncTask(ReputationDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ReputationItem... reputationItems) {
            dao.insert(reputationItems[0]);
            return null;
        }
    }

    private static class GetReputationAsyncTask extends AsyncTask<Short, Void, LiveData<ReputationItem>> {
        private final ReputationDao dao;

        public GetReputationAsyncTask(ReputationDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<ReputationItem> doInBackground(Short... shorts) {
            return dao.getItem(shorts[0]);
        }
    }

    private static class UpdateReputationAsyncTask extends AsyncTask<Short, Void, Void> {
        private final ReputationDao dao;

        public UpdateReputationAsyncTask(ReputationDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Short... shorts) {
            dao.updateReputation(Byte.parseByte(String.valueOf(shorts[0])), shorts[1]);
            return null;
        }
    }
}
