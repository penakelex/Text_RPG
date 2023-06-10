package penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.Skills.SkillAdapter;
import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingSkillsBinding;

public class SkillTableHelper {
    private CharacteristicsViewModel characteristicsViewModel = null;
    private final SkillsViewModel skillsViewModel;
    private final LifecycleOwner lifecycleOwner;

    public SkillTableHelper(CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, LifecycleOwner owner) {
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.lifecycleOwner = owner;
    }

    public SkillTableHelper(Activity activity) {
        this.skillsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SkillsViewModel.class);
        this.skillsViewModel.initiate(activity.getApplication());
        this.lifecycleOwner = (LifecycleOwner) activity;
    }

    @SuppressLint("DefaultLocale")
    public void choosingSkill(SharedPreferences sharedPreferences, byte ID, FragmentStartingSkillsBinding binding, Context context, LiveData<List<SkillsItem>> skills) {
        /*if (sharedPreferences.getInt(Main_Skills, 3) == 0) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.not_enough_main_skills_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else if (ID == -1) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.did_not_choosen_starting_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            skills.observe(lifecycleOwner, skillsItemList -> {
                skills.removeObservers(lifecycleOwner);
                sharedPreferences.edit().putInt(Main_Skills, sharedPreferences.getInt(Main_Skills, 3) + (skillsItemList.get(ID - 1).isMain() ? 1 : -1)).apply();
                skillsViewModel.updateIsMain(!skillsItemList.get(ID - 1).isMain(), ID);
                binding.skillsPoints.setText(String.format("%s %d", context.getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Main_Skills, 3)));
            });
        }*/
        if (ID == -1) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.did_not_choosen_starting_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            skills.observe(lifecycleOwner, skillsItemList -> {
                skills.removeObservers(lifecycleOwner);
                if (!skillsItemList.get(ID - 1).isMain() && sharedPreferences.getInt(Main_Skills, 3) == 0) {
                    Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.not_enough_main_skills_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                } else {
                    sharedPreferences.edit().putInt(Main_Skills, sharedPreferences.getInt(Main_Skills, 3) + (skillsItemList.get(ID - 1).isMain() ? 1 : -1)).apply();
                    binding.chooseAsMainSkill.setText(skillsItemList.get(ID - 1).isMain() ? context.getResources().getString(R.string.choose_as_not_main_skill) : context.getResources().getString(R.string.choose_as_main_skill) );
                    skillsViewModel.updateIsMain(!skillsItemList.get(ID - 1).isMain(), ID);
                    binding.skillsPoints.setText(String.format("%s %d", context.getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Main_Skills, 3)));
                }
            });
        }
    }

    public void updateSkillValues() {
        LiveData<List<CharacteristicItem>> characteristics = characteristicsViewModel.getAllCharacteristics();
        LiveData<List<SkillsItem>> skills = skillsViewModel.getAllSkills();
        Log.d("sadawdq", "qweoqkwrpoqwkrp");
        characteristics.observe(lifecycleOwner, characteristicItems -> {
            characteristics.removeObservers(lifecycleOwner);
            skills.observe(lifecycleOwner, skillsItems -> {
                byte strength = characteristicItems.get(0).getValue(), physique = characteristicItems.get(1).getValue(), dexterity = characteristicItems.get(2).getValue(), mentality = characteristicItems.get(3).getValue(), luckiness = characteristicItems.get(4).getValue(), watchfulness = characteristicItems.get(5).getValue(), attractiveness = characteristicItems.get(6).getValue();
                skillsViewModel.updateValue((byte) (skillsItems.get(0).isMain() ? 2 * (watchfulness * 2 + luckiness + physique + 10) : (watchfulness * 2 + luckiness + physique + 10)), (byte) 1);
                skillsViewModel.updateValue((byte) (skillsItems.get(1).isMain() ? 2 * (strength * 2 + watchfulness + luckiness + 10) : (strength * 2 + watchfulness + luckiness + 10)), (byte) 2);
                skillsViewModel.updateValue((byte) (skillsItems.get(2).isMain() ? 2 * (watchfulness + luckiness + physique + strength * 2 + 10) : (watchfulness + luckiness + physique + strength * 2 + 10)), (byte) 3);
                skillsViewModel.updateValue((byte) (skillsItems.get(3).isMain() ? 2 * (attractiveness * 3 + luckiness + mentality + 10) : (attractiveness * 3 + luckiness + mentality + 10)), (byte) 4);
                skillsViewModel.updateValue((byte) (skillsItems.get(4).isMain() ? 2 * (2 * (attractiveness * 3 + watchfulness + 10)) : (attractiveness * 3 + watchfulness + 10)), (byte) 5);
                skillsViewModel.updateValue((byte) (skillsItems.get(5).isMain() ? 2 * (physique * 2 + dexterity + mentality + 10) : (physique * 2 + dexterity + mentality + 10)), (byte) 6);
                skillsViewModel.updateValue((byte) (skillsItems.get(6).isMain() ? 2 * (mentality * 2 + watchfulness + 10) : (mentality * 2 + watchfulness + 10)), (byte) 7);
                skillsViewModel.updateValue((byte) (skillsItems.get(7).isMain() ? 2 * (mentality * 2 + watchfulness + 10) : (mentality * 2 + watchfulness + 10)), (byte) 8);
                skillsViewModel.updateValue((byte) (skillsItems.get(8).isMain() ? 2 * (mentality * 2 + watchfulness + dexterity + 10) : (mentality * 2 + watchfulness + dexterity + 10)), (byte) 9);
            });
        });
    }

    public void setAdapterInformation(SkillAdapter adapter, Context context) {
        LiveData<List<SkillsItem>> skills = skillsViewModel.getAllSkills();
        skills.observe(lifecycleOwner, skillsItemList -> {
            SkillsItem[] skillsItems = new SkillsItem[skillsItemList.size()];
            for (int i = 0; i < skillsItemList.size(); i++) skillsItems[i] = skillsItemList.get(i);
            adapter.setInformation(context, skillsItems);
        });
    }

    public void saveChanges(SkillsItem[] information, View view, Context context) {
        for (SkillsItem skillsItem : information) {
            skillsViewModel.updateValue(skillsItem.getValue(), skillsItem.getID());
        }
        Snackbar.make(view, context.getResources().getString(R.string.changes_saved), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
    }
}
