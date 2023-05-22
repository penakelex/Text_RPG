package penakelex.textRPG.homeland.Dialogs;

import static android.content.Context.MODE_PRIVATE;
import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Main.Constants.Money;
import static penakelex.textRPG.homeland.Main.Constants.Reputation;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.StatisticsDatabase.StatisticsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.R;

public class DialogActivityHelper {
    @SuppressLint("NonConstantResourceId")
    public static void replicaListener(Dialogs.Quote.CharacterQuote characterQuote, int[] array, short[] plusStatistics, Context context) {
        int checkingParameter = characterQuote.getChecking();
        if (checkingParameter != -1) {
            int checkingValue;
            if (checkingParameter >= 1 && checkingParameter <= 7) {
                checkingValue = CharacteristicsDatabase.getValue(new CharacteristicsDatabaseHelper(context).getReadableDatabase(), checkingParameter);
            } else if (checkingParameter >= 8 && checkingParameter <= 16) {
                checkingParameter -= 7;
                checkingValue = SkillsDatabase.getValueSkill(new SkillsDatabaseHelper(context).getReadableDatabase(), checkingParameter);
            } else {
                checkingParameter -= 16;
                checkingValue = TalentsDatabase.isHaving(checkingParameter, new SkillsDatabaseHelper(context).getReadableDatabase()) ? 1 : 0;
            }
            if (checkingValue >= characterQuote.getCheckingValue()) {
                array[0] += characterQuote.getReputation();
                array[1] += characterQuote.getCheckingValue();
                array[2] += characterQuote.getMoney();
                switch (characterQuote.getQuote()) {
                    case R.string.quotes6_character_quote0_1:
                        characterQuote.setNextStep(2);
                        break;
                    case R.string.quotes10_character_quote10_0:
                        characterQuote.setNextStep(11);
                        break;
                    case R.string.quotes10_character_quote11_0:
                        characterQuote.setNextStep(12);
                        break;
                    case R.string.quotes10_character_quote12_0:
                        characterQuote.setNextStep(13);
                        break;
                    case R.string.quotes10_character_quote13_0:
                        characterQuote.setNextStep(14);
                        break;
                }
                plusStatistics[0]++;
            }
        } else {
            array[0] += characterQuote.getReputation();
            array[1] += characterQuote.getCheckingValue();
            array[2] += characterQuote.getMoney();
        }
    }

    public static String getSettingValueForCharacterQuote(int quote, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
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

    public static void saveChanges(int[] array, ArrayList<Integer> itemsToAdd, short[] plusStatistics, View view, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        if (array[0] != 0 || array[1] != 0 || array[2] != 0) {
            sharedPreferences.edit().putInt(Money, sharedPreferences.getInt(Money, 0) + array[2]).
                    putInt(Reputation, sharedPreferences.getInt(Reputation, 0) + array[0]).
                    putInt(Experience, sharedPreferences.getInt(Experience, 0) + array[1]).apply();
        }
        if (itemsToAdd.size() != 0) {
            for (Integer item : itemsToAdd) {
                InventoryDatabaseHelper.insertNewItemToPlayersInventory(item, context, view);
            }
        }
        for (byte i = 0; i < plusStatistics.length; i++) {
            StatisticsDatabaseHelper.updateStatistic(context, plusStatistics[i], i);
        }
    }

    public static Drawable getTalkingCharacterImage(int image, Context context) {
        switch (image) {
            case 1:
                return ContextCompat.getDrawable(context, R.drawable.registrator);
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.instructor_serdcev);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.alena);
            case 4:
                return ContextCompat.getDrawable(context, R.drawable.andrey);
            case 5:
                return ContextCompat.getDrawable(context, R.drawable.filipp_2);
            default:
                return null;
        }
    }

    public static void updateLocationForDialog(int ID, int globalMapLocation, int localMapLocation, Context context, boolean isStatic) {
        context.getSharedPreferences(Homeland_Tag, MODE_PRIVATE).edit().putInt(ID_Dialog, ID).putInt(Global_Map_Location, globalMapLocation).putInt(Local_Map_Location, localMapLocation).putBoolean(Static_Position, isStatic).apply();
    }

    public static String getTalkingCharacterQuote(int quote, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        switch (quote) {
            case 1:
                return String.format("%s %s.", context.getResources().getString(R.string.ur_name), sharedPreferences.getString(Main_Character_Name, ""));
            case 4:
                return String.format("%s %s%s", context.getResources().getString(R.string.quotes4_quote6_0), sharedPreferences.getString(Main_Character_Name, ""), context.getResources().getString(R.string.quotes4_quote6_1));
            default:
                return String.valueOf(context.getResources().getString(quote));
        }
    }
}
