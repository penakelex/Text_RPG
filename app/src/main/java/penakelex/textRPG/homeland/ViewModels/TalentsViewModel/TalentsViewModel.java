package penakelex.textRPG.homeland.ViewModels.TalentsViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;

public class TalentsViewModel extends ViewModel {
    private TalentsRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new TalentsRepository(application);
    }

    public LiveData<List<TalentItem>> getAllTalents() {
        return repository.getAllTalents();
    }

    public void add(TalentItem talentItem) {
        repository.add(talentItem);
    }

    public void changeIsHaving(boolean isHaving, byte ID) {
        repository.changeIsHaving(isHaving, ID);
    }

    public LiveData<TalentItem> getTalent(byte ID) {
        return repository.getTalent(ID);
    }

    public LiveData<List<TalentItem>> getHavingTalents() {
        return repository.getHavingTalents();
    }
}
