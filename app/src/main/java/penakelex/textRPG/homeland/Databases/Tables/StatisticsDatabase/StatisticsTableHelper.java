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
/*    public static void settingStartingValues(Context context, String[] names) {
        StatisticsDao statisticsDao = StatisticsDatabase.getDatabase(context.getApplicationContext()).statisticsDao();
        statisticsDao.deleteAll();
        for (String name : names) statisticsDao.insert(new StatisticItem(name));
    }

    public static ArrayList<StatisticInformation> getStatisticsInformation(Context context) {
        ArrayList<StatisticInformation> arrayList = new ArrayList<>();
        List<StatisticItem> list = StatisticsDatabase.getDatabase(context.getApplicationContext()).statisticsDao().getAllStatistic();
        for (StatisticItem item : list)
            arrayList.add(new StatisticInformation(item.getName(), item.getCount()));
        return (ArrayList<StatisticInformation>) arrayList.clone();
    }

    public static void updateStatistic(Context context, short plusValue, byte ID) {
        if (plusValue != 0) {
            StatisticsDao dao = StatisticsDatabase.getDatabase(context).statisticsDao();
            short value = dao.getAllStatistic().get(ID).getCount();
            dao.updateCount((short) (value + plusValue), ID);
        }
    }
    public static int getStatisticsCount(Context context) {
        return StatisticsDatabase.getDatabase(context).statisticsDao().getAllStatistic().size();
    }*/
}
