package penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;

public class CharacteristicsViewModel extends ViewModel {
    private CharacteristicsRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new CharacteristicsRepository(application);
    }

    public List<CharacteristicItem> getAllCharacteristics() {
        return repository.getAllCharacteristics();
    }

    public void add(CharacteristicItem item) {
        repository.add(item);
    }

    public void update(byte newValue, byte ID) {
        repository.update(newValue, ID);
    }

    public CharacteristicItem getCharacteristic(byte ID) {
        return repository.getCharacteristic(ID);
    }
}
