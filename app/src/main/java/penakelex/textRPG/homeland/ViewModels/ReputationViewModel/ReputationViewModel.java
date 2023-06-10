package penakelex.textRPG.homeland.ViewModels.ReputationViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

    public LiveData<List<ReputationItem>> getAllReputation() {
        return repository.getAllReputations();
    }

    public LiveData<ReputationItem> getReputation(short ID) {
        return repository.getReputation(ID);
    }
}
