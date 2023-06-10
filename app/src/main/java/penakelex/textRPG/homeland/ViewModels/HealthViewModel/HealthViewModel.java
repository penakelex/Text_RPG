package penakelex.textRPG.homeland.ViewModels.HealthViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;

public class HealthViewModel extends ViewModel {
    private HealthRepository healthRepository = null;

    public void initiate(@NonNull Application application) {
        this.healthRepository = new HealthRepository(application);
    }

    public LiveData<List<HealthItem>> getAllHealthStatuses() {
        return healthRepository.getAllHealthStatuses();
    }

    public void add(HealthItem item) {
        healthRepository.add(item);
    }

    public void update(short newValue, byte ID) {
        healthRepository.update(newValue, ID);
    }
}
