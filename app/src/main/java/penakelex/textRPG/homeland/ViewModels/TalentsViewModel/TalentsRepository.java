package penakelex.textRPG.homeland.ViewModels.TalentsViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentsDao;

public class TalentsRepository {
    private final TalentsDao dao;

    public TalentsRepository(Application application) {
        this.dao = Database.getDatabase(application).talentsDao();
    }

    public LiveData<List<TalentItem>> getAllTalents() {
        try {
            return new GetAllTalentsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(TalentItem talentItem) {
        new AddTalentAsyncTask(dao).execute(talentItem);
    }

    public void changeIsHaving(boolean isHaving, byte ID) {
        new ChangeIsHavingAsyncTask(dao).execute(isHaving, ID);
    }

    public LiveData<TalentItem> getTalent(byte ID) {
        try {
            return new GetTalentAsyncTask(dao).execute(ID).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<List<TalentItem>> getHavingTalents() {
        try {
            return new GetHavingTalentsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class GetAllTalentsAsyncTask extends AsyncTask<Void, Void, LiveData<List<TalentItem>> > {
        private final TalentsDao dao;

        public GetAllTalentsAsyncTask(TalentsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<TalentItem>> doInBackground(Void... voids) {
            return dao.getAllTalents();
        }
    }

    private static class AddTalentAsyncTask extends AsyncTask<TalentItem, Void, Void> {
        private final TalentsDao dao;

        public AddTalentAsyncTask(TalentsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TalentItem... talentItems) {
            dao.add(talentItems[0]);
            return null;
        }
    }

    private static class ChangeIsHavingAsyncTask extends AsyncTask<Object, Void, Void> {
        private final TalentsDao dao;

        public ChangeIsHavingAsyncTask(TalentsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.changeIsHaving((boolean) objects[0], (byte) objects[1]);
            return null;
        }
    }

    private static class GetTalentAsyncTask extends AsyncTask<Byte, Void, LiveData<TalentItem>> {
        private final TalentsDao dao;

        public GetTalentAsyncTask(TalentsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<TalentItem> doInBackground(Byte... bytes) {
            return dao.getTalent(bytes[0]);
        }
    }

    private static class GetHavingTalentsAsyncTask extends AsyncTask<Void, Void, LiveData<List<TalentItem>>> {
        private final TalentsDao dao;

        public GetHavingTalentsAsyncTask(TalentsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<TalentItem>> doInBackground(Void... voids) {
            return dao.getHavingTalents(true);
        }
    }
}
