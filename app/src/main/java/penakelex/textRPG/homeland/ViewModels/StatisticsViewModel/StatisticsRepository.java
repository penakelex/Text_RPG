package penakelex.textRPG.homeland.ViewModels.StatisticsViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticsDao;

public class StatisticsRepository {
    private final StatisticsDao dao;

    public StatisticsRepository(Application application) {
        this.dao = Database.getDatabase(application).statisticsDao();
    }

    public LiveData<List<StatisticItem>> getAllStatistic() {
        try {
            return new GetAllStatisticsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(StatisticItem item) {
        new AddStatisticItemAsyncTask(dao).execute(item);
    }

    public void updateCount(short newCount, byte ID) {
        new UpdateCountAsyncTask(dao).execute(newCount, (short) ID);
    }

    private static class GetAllStatisticsAsyncTask extends AsyncTask<Void, Void, LiveData<List<StatisticItem>>> {
        private final StatisticsDao dao;

        public GetAllStatisticsAsyncTask(StatisticsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<StatisticItem>> doInBackground(Void... voids) {
            return dao.getAllStatistic();
        }
    }

    private static class AddStatisticItemAsyncTask extends AsyncTask<StatisticItem, Void, Void> {
        private final StatisticsDao dao;

        public AddStatisticItemAsyncTask(StatisticsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(StatisticItem... statisticItems) {
            dao.insert(statisticItems[0]);
            return null;
        }
    }

    private static class UpdateCountAsyncTask extends AsyncTask<Short, Void, Void> {
        private final StatisticsDao dao;

        public UpdateCountAsyncTask(StatisticsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Short... shorts) {
            dao.updateCount(shorts[0], Byte.parseByte(String.valueOf(shorts[1])));
            return null;
        }
    }
}
