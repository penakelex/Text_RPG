package penakelex.textRPG.homeland.ViewModels.StatisticsViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;

public class StatisticsViewModel extends ViewModel {
    private StatisticsRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new StatisticsRepository(application);
    }

    public LiveData<List<StatisticItem>> getAllStatistic() {
        return repository.getAllStatistic();
    }

    public void add(StatisticItem item) {
        repository.add(item);
    }

    public void updateCount(short newCount, byte ID) {
        repository.updateCount(newCount, ID);
    }
}
