package penakelex.textRPG.homeland.Databases.StatisticsDatabase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Adapters.Statistics.StatisticInformation;

public class StatisticsDatabaseHelper {
    public static void settingStartingValues(Context context, String[] names) {
        StatisticsDao statisticsDao = StatisticsDatabase.getDatabase(context.getApplicationContext()).statisticsDao();
        statisticsDao.deleteAll();
        for (String name : names) statisticsDao.insert(new StatisticItem(name));
    }
    public static ArrayList<StatisticInformation> getStatisticsInformation(Context context) {
        ArrayList<StatisticInformation> arrayList = new ArrayList<>();
        List<StatisticItem> list = StatisticsDatabase.getDatabase(context.getApplicationContext()).statisticsDao().getAllStatistic();
        for (StatisticItem item : list) arrayList.add(new StatisticInformation(item.getName(), item.getCount()));
        return (ArrayList<StatisticInformation>) arrayList.clone();
    }
}
