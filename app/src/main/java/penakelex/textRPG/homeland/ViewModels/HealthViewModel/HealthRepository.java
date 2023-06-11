package penakelex.textRPG.homeland.ViewModels.HealthViewModel;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthDao;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;

public class HealthRepository {
    private final HealthDao dao;

    public HealthRepository(Application application) {
        this.dao = Database.getDatabase(application).healthDao();
    }

    public List<HealthItem> getAllHealthStatuses() {
        try {
            return new GetAllHealthStatusesAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(HealthItem healthItem) {
        new AddHealthStatusAsyncTask(dao).execute(healthItem);
    }

    public void update(short newValue, byte ID) {
        new UpdateHealthStatusAsyncTask(dao).execute(newValue, (short) ID);
    }

    private static class AddHealthStatusAsyncTask extends AsyncTask<HealthItem, Void, Void> {
        private final HealthDao dao;

        public AddHealthStatusAsyncTask(HealthDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(HealthItem... healthItems) {
            dao.insert(healthItems[0]);
            return null;
        }
    }

    private static class UpdateHealthStatusAsyncTask extends AsyncTask<Short, Void, Void> {
        private final HealthDao dao;

        public UpdateHealthStatusAsyncTask(HealthDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Short... shorts) {
            dao.updateValue(shorts[0], Byte.parseByte(String.valueOf(shorts[1])));
            dao.updateBaseValue(shorts[0], Byte.parseByte(String.valueOf(shorts[1])));
            return null;
        }
    }

    private static class GetAllHealthStatusesAsyncTask extends AsyncTask<Void, Void, List<HealthItem>> {
        private final HealthDao dao;

        public GetAllHealthStatusesAsyncTask(HealthDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<HealthItem> doInBackground(Void... voids) {
            return dao.getAllHealthStatuses();
        }
    }
}
