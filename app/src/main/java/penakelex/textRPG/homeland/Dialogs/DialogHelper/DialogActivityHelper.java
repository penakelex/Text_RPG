package penakelex.textRPG.homeland.Dialogs.DialogHelper;

import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Main.Constants.Money;
import static penakelex.textRPG.homeland.Main.Constants.Reputation;
import static penakelex.textRPG.homeland.Main.Constants.Satisfactory_Value;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticsTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.Dialogs.Dialogs;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.InventoryViewModel.InventoryViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.QuestsViewModel.QuestsViewModel;
import penakelex.textRPG.homeland.ViewModels.ReputationViewModel.ReputationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.ViewModels.StatisticsViewModel.StatisticsViewModel;
import penakelex.textRPG.homeland.ViewModels.TalentsViewModel.TalentsViewModel;

public class DialogActivityHelper {
    private final CharacteristicsViewModel characteristicsViewModel;
    private final SkillsViewModel skillsViewModel;
    private final TalentsViewModel talentsViewModel;
    private final StatisticsTableHelper statisticsTableHelper;
    private final InventoryTableHelper inventoryTableHelper;
    private final QuestsViewModel questsViewModel;
    private final ReputationViewModel reputationViewModel;
    private final SharedPreferences sharedPreferences;

    public DialogActivityHelper(Activity activity) {
        ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) activity);
        this.characteristicsViewModel = viewModelProvider.get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(activity.getApplication());
        this.skillsViewModel = viewModelProvider.get(SkillsViewModel.class);
        skillsViewModel.initiate(activity.getApplication());
        this.talentsViewModel = viewModelProvider.get(TalentsViewModel.class);
        talentsViewModel.initiate(activity.getApplication());
        StatisticsViewModel statisticsViewModel = viewModelProvider.get(StatisticsViewModel.class);
        statisticsViewModel.initiate(activity.getApplication());
        this.statisticsTableHelper = new StatisticsTableHelper(statisticsViewModel);
        InventoryViewModel inventoryViewModel = viewModelProvider.get(InventoryViewModel.class);
        inventoryViewModel.initiate(activity.getApplication());
        OtherInformationViewModel otherInformationViewModel = viewModelProvider.get(OtherInformationViewModel.class);
        otherInformationViewModel.initiate(activity.getApplication());
        this.inventoryTableHelper = new InventoryTableHelper(inventoryViewModel, characteristicsViewModel, skillsViewModel, otherInformationViewModel);
        this.questsViewModel = viewModelProvider.get(QuestsViewModel.class);
        questsViewModel.initiate(activity.getApplication());
        this.reputationViewModel = viewModelProvider.get(ReputationViewModel.class);
        reputationViewModel.initiate(activity.getApplication());
        this.sharedPreferences = activity.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
    }

    @SuppressLint("NonConstantResourceId")
    public int replicaListener(Dialogs.Quote.CharacterQuote characterQuote, int[] array, short[] plusStatistics) {
        int checkingParameter = characterQuote.getChecking();
        if (checkingParameter != -1) {
            if (checkingParameter >= 1 && checkingParameter <= 7) {
                CharacteristicItem characteristic = characteristicsViewModel.getCharacteristic((byte) checkingParameter);
                sharedPreferences.edit().putBoolean(Satisfactory_Value, characteristic.getValue() >= characterQuote.getCheckingValue()).apply();
            } else if (checkingParameter >= 8 && checkingParameter <= 16) {
                checkingParameter -= 7;
                SkillsItem skill = skillsViewModel.getSkill((byte) checkingParameter);
                sharedPreferences.edit().putBoolean(Satisfactory_Value, skill.getValue() >= characterQuote.getCheckingValue()).apply();
            } else {
                checkingParameter -= 16;
                TalentItem talent = talentsViewModel.getTalent((byte) checkingParameter);
                sharedPreferences.edit().putBoolean(Satisfactory_Value, (talent.isHaving() ? 1 : 0) == characterQuote.getCheckingValue()).apply();
            }
            if (sharedPreferences.getBoolean(Satisfactory_Value, false)) {
                array[0] += characterQuote.getReputation();
                array[1] += characterQuote.getCheckingValue();
                array[2] += characterQuote.getMoney();
                switch (characterQuote.getQuote()) {
                    case R.string.quotes6_character_quote0_1 -> characterQuote.setNextStep(2);
                    case R.string.quotes10_character_quote10_0 -> characterQuote.setNextStep(11);
                    case R.string.quotes10_character_quote11_0 -> characterQuote.setNextStep(12);
                    case R.string.quotes10_character_quote12_0 -> characterQuote.setNextStep(13);
                    case R.string.quotes10_character_quote13_0 -> characterQuote.setNextStep(14);
                }
                plusStatistics[0]++;
            }
        } else {
            array[0] += characterQuote.getReputation();
            array[1] += characterQuote.getCheckingValue();
            array[2] += characterQuote.getMoney();
        }
        return characterQuote.getNextStep();
    }

    public String getSettingValueForCharacterQuote(int quote, Context context) {
        switch (quote) {
            case 2:
                return String.format("%s %s.", context.getResources().getString(R.string.quotes4_character_quote0_0), sharedPreferences.getString(Main_Character_Name, ""));
            case 3:
                return String.format("%s %s.", context.getResources().getString(R.string.quotes4_character_quote2_0), sharedPreferences.getString(Main_Character_Name, ""));
            case 5:
                switch (sharedPreferences.getInt(Gender, -1)) {
                    case 0:
                        return context.getResources().getString(R.string.quotes6_character_quote0_0_0);
                    case 1:
                        return context.getResources().getString(R.string.quotes6_character_quote0_0_1);
                }
        }
        return context.getResources().getString(quote);
    }

    public void saveChanges(int[] array, ArrayList<ShortItemInformation> itemsToAdd, short[] plusStatistics, View view, Context context) {
        if (array[0] != 0 || array[1] != 0 || array[2] != 0) {
            sharedPreferences.edit().putInt(Money, sharedPreferences.getInt(Money, 0) + array[2]).
                    putInt(Reputation, sharedPreferences.getInt(Reputation, 0) + array[0]).
                    putInt(Experience, sharedPreferences.getInt(Experience, 0) + array[1]).apply();
        }
        if (itemsToAdd.size() != 0) {
            for (ShortItemInformation information : itemsToAdd) {
                if (information.getOwner() == 1) {
                    inventoryTableHelper.insertNewItemToPlayersInventory(information.getID(), context, view);
                } else {
                    inventoryTableHelper.insertNewItemToSomeoneInventory(information.getID(), information.getOwner());
                }
            }
        }
        for (byte i = 0; i < plusStatistics.length; i++) {
            statisticsTableHelper.updateStatistic(plusStatistics[i], i);
        }
    }

    public static Drawable getTalkingCharacterImage(int image, Context context) {
        return switch (image) {
            case 1 -> ContextCompat.getDrawable(context, R.drawable.registrator);
            case 2 -> ContextCompat.getDrawable(context, R.drawable.instructor_serdcev);
            case 3 -> ContextCompat.getDrawable(context, R.drawable.alena);
            case 4 -> ContextCompat.getDrawable(context, R.drawable.andrey);
            case 5 -> ContextCompat.getDrawable(context, R.drawable.filipp_2);
            case 6 -> ContextCompat.getDrawable(context, R.drawable.doctor);
            default -> null;
        };
    }

    public void updateLocationForDialog(int ID, int globalMapLocation, int localMapLocation, boolean isStatic) {
        sharedPreferences.edit().putInt(ID_Dialog, ID).putInt(Global_Map_Location, globalMapLocation).putInt(Local_Map_Location, localMapLocation).putBoolean(Static_Position, isStatic).apply();
    }

    public String getTalkingCharacterQuote(int quote, Context context) {
        return switch (quote) {
            case 1 ->
                    String.format("%s %s.", context.getResources().getString(R.string.ur_name), sharedPreferences.getString(Main_Character_Name, ""));
            case 4 ->
                    String.format("%s %s%s", context.getResources().getString(R.string.quotes4_quote6_0), sharedPreferences.getString(Main_Character_Name, ""), context.getResources().getString(R.string.quotes4_quote6_1));
            default -> String.valueOf(context.getResources().getString(quote));
        };
    }

    public void updateQuestStage(short stage, short ID) {
        questsViewModel.updateQuestStage(stage, ID);
    }

    public void addQuest(int name) {
        if (questsViewModel.getQuest(name) == null) {
            questsViewModel.add(new QuestItem(name));
        }
    }

    public void putBooleansToSharedPreferences(BooleanSP... booleanSPs) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (BooleanSP booleanSP : booleanSPs) {
            editor.putBoolean(booleanSP.getTag(), booleanSP.getValue());
        }
        editor.apply();
    }

    public void putIntsToSharedPreferences(IntSP... intSPs) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (IntSP intSP : intSPs) {
            editor.putInt(intSP.getTag(), intSP.getValue());
        }
        editor.apply();
    }

    public void addReputation(int... names) {
        for (int name : names) {
            if (reputationViewModel.getReputation(name) == null) {
                reputationViewModel.add(new ReputationItem(name));
            }
        }
    }
}
