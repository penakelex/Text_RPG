package penakelex.textRPG.homeland.ViewModels.QuestsViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;

public class QuestsViewModel extends ViewModel {
    private QuestsRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new QuestsRepository(application);
    }

    public List<QuestItem> getAllQuests() {
        return repository.getAllQuests();
    }

    public void add(QuestItem questItem) {
        repository.add(questItem);
    }

    public void updateQuestStage(short newStage, int ID) {
        repository.updateQuestStage(newStage, ID);
    }
}
