package penakelex.textRPG.homeland.ViewModels.SkillsViewModel;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import penakelex.textRPG.homeland.Databases.Database.Database;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsDao;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;

public class SkillsRepository {
    private final SkillsDao dao;

    public SkillsRepository(Application application) {
        this.dao = Database.getDatabase(application).skillsDao();
    }

    public void add(SkillsItem item) {
        new AddSkillAsyncTask(dao).execute(item);
    }

    public void updateValue(byte newValue, byte ID) {
        new UpdateSkillValueAsyncTask(dao).execute(newValue, ID);
    }

    public void updateIsMain(byte isMain, byte ID) {
        new UpdateSkillIsMainAsyncTask(dao).execute(isMain, ID);
    }

    public List<SkillsItem> getAllSkills() {
        try {
            return new GetAllSkillsAsyncTask(dao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SkillsItem getSkill(byte ID) {
        try {
            return new GetSkillAsyncTask(dao).execute(ID).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class AddSkillAsyncTask extends AsyncTask<SkillsItem, Void, Void> {
        private final SkillsDao dao;

        public AddSkillAsyncTask(SkillsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SkillsItem... skillsItems) {
            dao.add(skillsItems[0]);
            return null;
        }
    }

    private static class UpdateSkillValueAsyncTask extends AsyncTask<Byte, Void, Void> {
        private final SkillsDao dao;

        public UpdateSkillValueAsyncTask(SkillsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Byte... bytes) {
            dao.updateValue(bytes[0], bytes[1]);
            return null;
        }
    }

    private static class UpdateSkillIsMainAsyncTask extends AsyncTask<Byte, Void, Void> {
        private final SkillsDao dao;

        public UpdateSkillIsMainAsyncTask(SkillsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Byte... bytes) {
            dao.updateIsMain(bytes[0], bytes[1]);
            return null;
        }
    }

    private static class GetAllSkillsAsyncTask extends AsyncTask<Void, Void, List<SkillsItem>> {
        private final SkillsDao dao;

        public GetAllSkillsAsyncTask(SkillsDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<SkillsItem> doInBackground(Void... voids) {
            return dao.getAllSkills();
        }
    }

    private static class GetSkillAsyncTask extends AsyncTask<Byte, Void, SkillsItem> {
        private final SkillsDao dao;

        public GetSkillAsyncTask(SkillsDao dao) {
            this.dao = dao;
        }

        @Override
        protected SkillsItem doInBackground(Byte... bytes) {
            return dao.getSkill(bytes[0]);
        }
    }
}
