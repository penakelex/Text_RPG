package penakelex.textRPG.homeland.ViewModels.ReputationViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;

public class ReputationViewModel extends ViewModel {
    private ReputationRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new ReputationRepository(application);
    }

    public void add(ReputationItem item) {
        repository.add(item);
    }

    public void update(byte newReputation, short ID) {
        repository.update(newReputation, ID);
    }

    public List<ReputationItem> getAllReputation() {
        return repository.getAllReputations();
    }

    public ReputationItem getReputation(int name) {
        return repository.getReputation(name);
    }
}
