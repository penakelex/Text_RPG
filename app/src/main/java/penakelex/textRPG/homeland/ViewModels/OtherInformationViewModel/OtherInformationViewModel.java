package penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;

public class OtherInformationViewModel extends ViewModel {
    private OtherInformationRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new OtherInformationRepository(application);
    }

    public void add(OtherInformationItem item) {
        repository.add(item);
    }

    public LiveData<OtherInformationItem> getOtherInformationItemByID(byte ID) {
        return repository.getOtherInformationItem(ID);
    }

    public LiveData<List<OtherInformationItem>> getAllOtherInformation() {
        return repository.getAllOtherInformation();
    }

    public void updateInformation(int newValue, byte ID) {
        repository.updateInformation(newValue, ID);
    }
}
