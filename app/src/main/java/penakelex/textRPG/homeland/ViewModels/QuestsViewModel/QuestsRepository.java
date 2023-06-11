package penakelex.textRPG.homeland.ViewModels.QuestsViewModel;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestsDao;

public class QuestsRepository {
    private final QuestsDao dao;

    public QuestsRepository(Application application) {
        this.dao = Database.getDatabase(application).questsDao();
    }

    public List<QuestItem> getAllQuests() {
        try {
            return new GetAllQuestsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(QuestItem questItem) {
        new AddQuestAsyncTask(dao).execute(questItem);
    }

    public void updateQuestStage(short newStage, int ID) {
        new UpdateQuestStageAsyncTask(dao).execute(newStage, ID);
    }

    private static class GetAllQuestsAsyncTask extends AsyncTask<Void, Void, List<QuestItem>> {
        private final QuestsDao dao;

        public GetAllQuestsAsyncTask(QuestsDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<QuestItem> doInBackground(Void... voids) {
            return dao.getQuests();
        }
    }

    private static class AddQuestAsyncTask extends AsyncTask<QuestItem, Void, Void> {
        private final QuestsDao dao;

        public AddQuestAsyncTask(QuestsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(QuestItem... questItems) {
            dao.addQuest(questItems[0]);
            return null;
        }
    }

    private static class UpdateQuestStageAsyncTask extends AsyncTask<Object, Void, Void> {
        private final QuestsDao dao;

        public UpdateQuestStageAsyncTask(QuestsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.updateQuestStage((short) objects[0], (int) objects[1]);
            return null;
        }
    }
}
