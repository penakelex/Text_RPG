package penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Is_All_Good;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.ViewModels.TalentsViewModel.TalentsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingTalentsBinding;

public class TalentsTableHelper {
    private final CharacteristicsViewModel characteristicsViewModel;
    private final SkillsViewModel skillsViewModel;
    private final TalentsViewModel talentsViewModel;
    private final OtherInformationViewModel otherInformationViewModel;

    public TalentsTableHelper(CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, TalentsViewModel talentsViewModel, @NonNull OtherInformationViewModel otherInformationViewModel) {
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.talentsViewModel = talentsViewModel;
        this.otherInformationViewModel = otherInformationViewModel;
    }

    public void choosingTalent(SharedPreferences sharedPreferences, byte ID, FragmentStartingTalentsBinding binding, Context context) {
        if (sharedPreferences.getInt(Talents_Points, 2) == 0 && !talentsViewModel.getTalent(ID).isHaving()) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.not_enough_talents_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            switch (ID) {
                case 1 -> choosingSinger(sharedPreferences, ID, binding.getRoot(), context);
                case 2 -> choosingBull(sharedPreferences, ID, binding.getRoot(), context);
                case 3 -> choosingStrongKick(sharedPreferences, ID);
                case 4 -> choosingExperienced(sharedPreferences, ID, binding.getRoot(), context);
                case 5 -> choosingTrained(sharedPreferences, ID, binding.getRoot(), context);
                case 6 -> choosingHeavyweight(sharedPreferences, ID, binding.getRoot(), context);
                case 7 -> choosingKindOne(sharedPreferences, ID, binding.getRoot(), context);
                default ->
                        Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.have_not_chosen_talent), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        }
    }

    private void choosingKindOne(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        List<SkillsItem> skills = skillsViewModel.getAllSkills();
        if (talent.isHaving()) {
            if (skillsAreReady(skills, true)) {
                updateSkillsValues(skills, true);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        } else {
            if (skillsAreReady(skills, false)) {
                updateSkillsValues(skills, false);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        }
        talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
    }


    private void updateSkillsValues(List<SkillsItem> skills, boolean isHaving) {
        byte[] plusValues;
        if (isHaving) {
            plusValues = new byte[]{5, 5, 5, -5, -5, 0, -5, -5, -5};
        } else {
            plusValues = new byte[]{-5, -5, -5, 5, 5, 0, 5, 5, 5};
        }
        for (int i = 0; i < skills.size(); i++) {
            skillsViewModel.updateValue((byte) (skills.get(i).getValue() + plusValues[i]), skills.get(i).getID());
        }
    }

    private boolean skillsAreReady(List<SkillsItem> skills, boolean isHaving) {
        if (isHaving) {
            return ((skills.get(0).getValue() + 5 <= 100) && (skills.get(1).getValue() + 5 <= 100) && (skills.get(2).getValue() + 5 <= 100)) && ((skills.get(3).getValue() - 5 >= 0) && (skills.get(4).getValue() - 5 >= 0) && (skills.get(6).getValue() - 5 >= 0) && (skills.get(7).getValue() - 5 >= 0) && (skills.get(8).getValue() - 5 >= 0));
        } else {
            return ((skills.get(0).getValue() - 5 >= 0) && (skills.get(1).getValue() - 5 >= 0) && (skills.get(2).getValue() - 5 >= 0) && ((skills.get(3).getValue() + 5 <= 100) && (skills.get(4).getValue() + 5 <= 100) && (skills.get(6).getValue() + 5 <= 100) && (skills.get(7).getValue() + 5 <= 100) && (skills.get(8).getValue() + 5 <= 100)));
        }
    }

    private void choosingHeavyweight(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        OtherInformationItem carryWeight = otherInformationViewModel.getOtherInformationItemByID((byte) 3);
        if (talent.isHaving()) {
            if (carryWeight.getValue() - 20 > 0) {
                otherInformationViewModel.updateInformation(carryWeight.getValue() - 20, carryWeight.getID());
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        } else {
            otherInformationViewModel.updateInformation(carryWeight.getValue() + 20, carryWeight.getID());
            sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
        }
        talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
    }

    private void choosingTrained(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        List<CharacteristicItem> characteristics = characteristicsViewModel.getAllCharacteristics();
        List<SkillsItem> skills = skillsViewModel.getAllSkills();
        boolean isAllGood = true;
        if (talent.isHaving()) {
            for (CharacteristicItem characteristic : characteristics) {
                if (characteristic.getValue() == 0) {
                    isAllGood = false;
                    break;
                }
            }
        } else {
            for (CharacteristicItem characteristic : characteristics) {
                if (characteristic.getValue() == 5) {
                    isAllGood = false;
                    break;
                }
            }
        }
        sharedPreferences.edit().putBoolean(Is_All_Good, isAllGood).apply();
        if (isAllGood) {
            if (talent.isHaving()) {
                for (SkillsItem skill : skills) {
                    if (skill.getValue() + 10 > 100) {
                        isAllGood = false;
                        break;
                    }
                }
            } else {
                for (SkillsItem skill : skills) {
                    if (skill.getValue() - 10 < 0) {
                        isAllGood = false;
                        break;
                    }
                }
            }
            sharedPreferences.edit().putBoolean(Is_All_Good, isAllGood).apply();
        }
        if (isAllGood) {
            if (talent.isHaving()) {
                for (CharacteristicItem characteristic : characteristics) {
                    characteristicsViewModel.update((byte) (characteristic.getValue() - 1), characteristic.getID());
                }
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                for (CharacteristicItem characteristic : characteristics) {
                    characteristicsViewModel.update((byte) (characteristic.getValue() + 1), characteristic.getID());
                }
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
            }
            if (talent.isHaving()) {
                for (SkillsItem skill : skills) {
                    skillsViewModel.updateValue((byte) (skill.getValue() + 10), skill.getID());
                }
            } else {
                for (SkillsItem skill : skills) {
                    skillsViewModel.updateValue((byte) (skill.getValue() - 10), skill.getID());
                }
            }
            talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void choosingExperienced(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        List<SkillsItem> skills = skillsViewModel.getAllSkills();
        boolean isAllGood = true;
        if (talent.isHaving()) {
            for (SkillsItem skillsItem : skills) {
                if (skillsItem.getValue() - 10 < 0) {
                    isAllGood = false;
                    break;
                }
            }
        } else {
            for (SkillsItem skillsItem : skills) {
                if (skillsItem.getValue() + 10 > 100) {
                    isAllGood = false;
                    break;
                }
            }
        }
        if (isAllGood) {
            if (talent.isHaving()) {
                for (SkillsItem skillsItem : skills) {
                    skillsViewModel.updateValue((byte) (skillsItem.getValue() - 10), skillsItem.getID());
                }
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                for (SkillsItem skillsItem : skills) {
                    skillsViewModel.updateValue((byte) (skillsItem.getValue() + 10), skillsItem.getID());
                }
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
            }
            talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void choosingStrongKick(SharedPreferences sharedPreferences, byte ID) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        OtherInformationItem meleeDamage = otherInformationViewModel.getOtherInformationItemByID((byte) 5),
                critical = otherInformationViewModel.getOtherInformationItemByID((byte) 6);
        if (talent.isHaving()) {
            otherInformationViewModel.updateInformation(meleeDamage.getValue() - 1, (byte) 5);
        } else {
            otherInformationViewModel.updateInformation(meleeDamage.getValue() + 1, (byte) 5);
        }
        if (talent.isHaving()) {
            otherInformationViewModel.updateInformation(critical.getValue() + 5, (byte) 6);
            sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
        } else {
            otherInformationViewModel.updateInformation(critical.getValue() - 5, (byte) 6);
            sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
        }
        talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
    }

    private void choosingBull(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        OtherInformationItem ap = otherInformationViewModel.getOtherInformationItemByID((byte) 2);
        CharacteristicItem strength = characteristicsViewModel.getCharacteristic((byte) 1),
                physique = characteristicsViewModel.getCharacteristic((byte) 2);
        boolean isAllGood;
        if (talent.isHaving()) {
            isAllGood = strength.getValue() > 0;
        } else {
            isAllGood = strength.getValue() < 5;
        }
        if (isAllGood) {
            if (talent.isHaving()) {
                sharedPreferences.edit().putBoolean(Is_All_Good, physique.getValue() > 0).apply();
            } else {
                sharedPreferences.edit().putBoolean(Is_All_Good, physique.getValue() < 5).apply();
            }
        }
        if (isAllGood) {
            if (talent.isHaving()) {
                characteristicsViewModel.update((byte) (strength.getValue() - 1), strength.getID());
            } else {
                characteristicsViewModel.update((byte) (strength.getValue() + 1), strength.getID());
            }
            if (talent.isHaving()) {
                characteristicsViewModel.update((byte) (physique.getValue() - 1), physique.getID());
            } else {
                characteristicsViewModel.update((byte) (physique.getValue() + 1), physique.getID());
            }
            if (talent.isHaving()) {
                otherInformationViewModel.updateInformation(ap.getValue() - 2, (byte) 2);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                otherInformationViewModel.updateInformation(ap.getValue() + 2, (byte) 2);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
            }
            talentsViewModel.changeIsHaving(!talent.isHaving(), ID);
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void choosingSinger(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        TalentItem talent = talentsViewModel.getTalent(ID);
        CharacteristicItem characteristic = characteristicsViewModel.getCharacteristic((byte) 7);
        if (talent.isHaving()) {
            if (characteristic.getValue() > 0) {
                characteristicsViewModel.update((byte) (characteristic.getValue() - 1), (byte) 7);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        } else {
            if (characteristic.getValue() < 5) {
                characteristicsViewModel.update((byte) (characteristic.getValue() + 1), (byte) 7);
                sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        }
        talentsViewModel.changeIsHaving(!talent.isHaving(), talent.getID());
    }
}
