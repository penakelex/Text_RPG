package penakelex.textRPG.homeland.Databases.Tables.HealthDatabase;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.ViewModels.HealthViewModel.HealthViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;

public class HealthTableHelper {
    private final HealthViewModel healthViewModel;
    private final OtherInformationViewModel otherInformationViewModel;
    private final LifecycleOwner lifecycleOwner;

    public HealthTableHelper(HealthViewModel healthViewModel, OtherInformationViewModel otherInformationViewModel, LifecycleOwner lifecycleOwner) {
        this.healthViewModel = healthViewModel;
        this.otherInformationViewModel = otherInformationViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public HealthTableHelper(Activity activity) {
        this.healthViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(HealthViewModel.class);
        this.healthViewModel.initiate(activity.getApplication());
        this.otherInformationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(OtherInformationViewModel.class);
        this.otherInformationViewModel.initiate(activity.getApplication());
        this.lifecycleOwner = (LifecycleOwner) activity;
    }

    public void updateInformation() {
        LiveData<OtherInformationItem> healthPoints = otherInformationViewModel.getOtherInformationItemByID((byte) 1);
        healthPoints.observe(lifecycleOwner, item -> {
            healthPoints.removeObservers(lifecycleOwner);
            healthViewModel.update((short) item.getValue(), (byte) 1);
        });
    }
    /*public static ArrayList<HealthStatusInformation> getHealthStatusInformation(Context context) {
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
        healthDao.insert(new HealthItem(names[0], (short) OtherInformationTableHelper.getValue(new OtherInformationDatabaseHelper(context).getReadableDatabase(), 7)));
        for (int i = 1; i < names.length; i++) {
            healthDao.insert(new HealthItem(names[i], (short) 1));
        }
    }*/
}
