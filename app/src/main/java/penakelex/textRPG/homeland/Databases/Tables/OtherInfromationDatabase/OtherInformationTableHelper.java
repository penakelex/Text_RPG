package penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;

public class OtherInformationTableHelper {
    private final OtherInformationViewModel otherInformationViewModel;
    private final CharacteristicsViewModel characteristicsViewModel;
    private final SkillsViewModel skillsViewModel;

    public OtherInformationTableHelper(OtherInformationViewModel otherInformationViewModel, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel) {
        this.otherInformationViewModel = otherInformationViewModel;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
    }

    public OtherInformationTableHelper(Activity activity) {
        this.otherInformationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(OtherInformationViewModel.class);
        this.otherInformationViewModel.initiate(activity.getApplication());
        this.characteristicsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(CharacteristicsViewModel.class);
        this.characteristicsViewModel.initiate(activity.getApplication());
        this.skillsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SkillsViewModel.class);
        this.skillsViewModel.initiate(activity.getApplication());
    }

    public void updateInformation() {
        List<CharacteristicItem> characteristics = characteristicsViewModel.getAllCharacteristics();
        List<SkillsItem> skills = skillsViewModel.getAllSkills();
        otherInformationViewModel.updateInformation(0, (byte) 1);
        otherInformationViewModel.updateInformation(Math.max(characteristics.get(2).getValue() + characteristics.get(1).getValue(), 5), (byte) 2);
        otherInformationViewModel.updateInformation(characteristics.get(0).getValue() * 10 + characteristics.get(2).getValue() * 3 + 20, (byte) 3);
        otherInformationViewModel.updateInformation(characteristics.get(0).getValue() * 2000 + characteristics.get(2).getValue() * 1000 + 10000, (byte) 4);
        otherInformationViewModel.updateInformation(characteristics.get(0).getValue() + characteristics.get(2).getValue() / 2 + skills.get(2).getValue() / 20, (byte) 5);
        otherInformationViewModel.updateInformation(characteristics.get(4).getValue() * 2 + characteristics.get(2).getValue() + characteristics.get(5).getValue() / 2 + skills.get(6).getValue() / 25, (byte) 6);
        otherInformationViewModel.updateInformation(characteristics.get(1).getValue() * 2 + skills.get(5).getValue() / 2, (byte) 7);
    }
}
