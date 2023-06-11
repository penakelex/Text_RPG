package penakelex.textRPG.homeland.ViewModels.SkillsViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;

public class SkillsViewModel extends ViewModel {
    private SkillsRepository repository = null;

    public void initiate(@NonNull Application application) {
        this.repository = new SkillsRepository(application);
    }

    public List<SkillsItem> getAllSkills() {
        return repository.getAllSkills();
    }

    public void add(SkillsItem item) {
        repository.add(item);
    }

    public void updateValue(byte newValue, byte ID) {
        repository.updateValue(newValue, ID);
    }

    public void updateIsMain(byte isMain, byte ID) {
        repository.updateIsMain(isMain, ID);
    }

    public SkillsItem getSkill(byte ID) {
        return repository.getSkill(ID);
    }
}
