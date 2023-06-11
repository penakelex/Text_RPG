package penakelex.textRPG.homeland.Databases.Tables.HealthDatabase;


import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.ViewModels.HealthViewModel.HealthViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;

public class HealthTableHelper {
    private final HealthViewModel healthViewModel;
    private final OtherInformationViewModel otherInformationViewModel;

    public HealthTableHelper(Activity activity) {
        this.healthViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(HealthViewModel.class);
        this.healthViewModel.initiate(activity.getApplication());
        this.otherInformationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(OtherInformationViewModel.class);
        this.otherInformationViewModel.initiate(activity.getApplication());
    }

    public void updateInformation() {
        OtherInformationItem healthPoints = otherInformationViewModel.getOtherInformationItemByID((byte) 7);
        healthViewModel.update((short) healthPoints.getValue(), (byte) 1);
        Log.d("healthPointsTetValue()", String.valueOf(healthPoints.getValue()));
    }
}
