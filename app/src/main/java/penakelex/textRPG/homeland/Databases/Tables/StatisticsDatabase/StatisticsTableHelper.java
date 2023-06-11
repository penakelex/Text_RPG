package penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase;

import java.util.List;

import penakelex.textRPG.homeland.ViewModels.StatisticsViewModel.StatisticsViewModel;

public class StatisticsTableHelper {
    private final StatisticsViewModel statisticsViewModel;

    public StatisticsTableHelper(StatisticsViewModel statisticsViewModel) {
        this.statisticsViewModel = statisticsViewModel;
    }

    public void updateStatistic(short plusStatistic, byte position) {
        List<StatisticItem> statistics = statisticsViewModel.getAllStatistic();
        statisticsViewModel.updateCount((short) (statistics.get(position).getCount() + plusStatistic), statistics.get(position).getID());
    }
}
