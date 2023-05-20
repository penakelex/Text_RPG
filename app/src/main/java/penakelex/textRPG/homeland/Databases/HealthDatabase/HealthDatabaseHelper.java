package penakelex.textRPG.homeland.Databases.HealthDatabase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Adapters.HealthStatuses.HealthStatusInformation;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;

public class HealthDatabaseHelper {
    public static ArrayList<HealthStatusInformation> getHealthStatusInformation(Context context) {
        ArrayList<HealthStatusInformation> arrayList = new ArrayList<>();
        List<HealthItem> list = HealthDatabase.getDatabase(context).healthDao().getAllHealthStatuses();
        for (HealthItem item : list) {
            arrayList.add(new HealthStatusInformation(item.getName(), item.getValue(), item.getBaseValue()));
        }
        return (ArrayList<HealthStatusInformation>) arrayList.clone();
    }

    public static void settingStartingValues(Context context, String[] names) {
        HealthDao healthDao = HealthDatabase.getDatabase(context).healthDao();
        healthDao.deleteAll();
        healthDao.insert(new HealthItem(names[0], (short) OtherInformationDatabase.getValue(new OtherInformationDatabaseHelper(context).getReadableDatabase(), 7)));
        for (int i = 1; i < names.length; i++) {
            healthDao.insert(new HealthItem(names[i], (short) 1));
        }
    }
}
