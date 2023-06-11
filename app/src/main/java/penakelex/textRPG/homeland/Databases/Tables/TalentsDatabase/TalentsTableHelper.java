package penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Is_All_Good;
import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;
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



    /*public static boolean[] getIsHaving(SQLiteDatabase database) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        boolean[] isHaving;
        Cursor cursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        cursor.moveToFirst();
        do {
            arrayList.add(cursor.getInt(isHavingColumnIndex) == 1);
        } while (cursor.moveToNext());
        cursor.close();
        isHaving = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) isHaving[i] = arrayList.get(i);
        return isHaving;
    }

    public static ArrayList<StartingTalentsInformation> getStartingTalentsInformation(SQLiteDatabase database) {
        ArrayList<StartingTalentsInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Name),
                isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        cursor.moveToFirst();
        do {
            arrayList.add(new StartingTalentsInformation(cursor.getString(nameColumnIndex), cursor.getInt(isHavingColumnIndex) == 1));
        } while (cursor.moveToNext());
        cursor.close();
        return arrayList;
    }

    public static ArrayList<CapabilitiesInformation> getHavingTalents(Context context) {
        Cursor cursor = new TalentsDatabaseHelper(context).getReadableDatabase().query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        int isHavingColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having),
                idColumnIndex = cursor.getColumnIndex(TalentsDatabaseHelper.KEY_ID), ID, count = 0;
        ArrayList<CapabilitiesInformation> information = new ArrayList<>();
        cursor.moveToFirst();
        do {
            if (cursor.getInt(isHavingColumnIndex) == 1) {
                ID = cursor.getInt(idColumnIndex);
                switch (ID) {
                    case 1:
                        information.add(new CapabilitiesInformation("Певец", ID));
                        break;
                    case 2:
                        information.add(new CapabilitiesInformation("Бугай", ID));
                        break;
                    case 3:
                        information.add(new CapabilitiesInformation("Сильный удар", ID));
                        break;
                    case 4:
                        information.add(new CapabilitiesInformation("Опытный", ID));
                        break;
                    case 5:
                        information.add(new CapabilitiesInformation("Натренированный", ID));
                        break;
                    case 6:
                        information.add(new CapabilitiesInformation("Тяжеловес", ID));
                        break;
                    case 7:
                        information.add(new CapabilitiesInformation("Добрый малый", ID));
                        break;
                }
                count++;
            }
        } while (cursor.moveToNext() && count < 2);
        cursor.close();
        return information;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingKindOne(SQLiteDatabase talentsDatabase, SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase) {
        if (isHaving(7, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    7));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    7));
        }
        SkillTableHelper.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
        return true;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingHeavyweight(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase) {
        int loadCapacity = OtherInformationTableHelper.getValue(infoDatabase, 3);
        Log.d("weight", String.valueOf(OtherInformationTableHelper.getValue(infoDatabase, 3)));
        if (isHaving(6, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    6));
            infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    OtherInformationDatabaseHelper.Table_Other_Info,
                    OtherInformationDatabaseHelper.KEY_Value,
                    loadCapacity - 20,
                    OtherInformationDatabaseHelper.KEY_ID,
                    3));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    6));
            infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                    OtherInformationDatabaseHelper.Table_Other_Info,
                    OtherInformationDatabaseHelper.KEY_Value,
                    loadCapacity + 20,
                    OtherInformationDatabaseHelper.KEY_ID,
                    3));
        }
        Log.d("weight", String.valueOf(OtherInformationTableHelper.getValue(infoDatabase, 3)));
        return loadCapacity >= 20;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingTrained(SQLiteDatabase talentsDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        int[] characteristics = CharacteristicsTableHelper.getCharacteristicsValues(characteristicsDatabase);
        boolean isAllGood = true;
        if (isHaving(5, talentsDatabase)) {
            for (int characteristic : characteristics) {
                if (characteristic - 1 < 0) {
                    isAllGood = false;
                    break;
                }
            }
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        5));
                for (int i = 0; i < characteristics.length; i++) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            characteristics[i] - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            i + 1));
                }
                SkillTableHelper.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
            }
        } else {
            for (int characteristic : characteristics) {
                if (characteristic + 1 > 5) {
                    isAllGood = false;
                    break;
                }
            }
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        5));
                for (int i = 0; i < characteristics.length; i++) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            characteristics[i] + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            i + 1));
                }
                SkillTableHelper.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
            }
        }
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingExperienced(SQLiteDatabase talentsDatabase, SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicsDatabase) {
        if (isHaving(4, talentsDatabase)) {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    4));
        } else {
            talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                    TalentsDatabaseHelper.Table_Talents,
                    TalentsDatabaseHelper.KEY_Having,
                    TalentsDatabaseHelper.KEY_ID,
                    4));
        }
        SkillTableHelper.settingNotStartingSkillsInDatabase(skillsDatabase, characteristicsDatabase, talentsDatabase);
        return true;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingStrongKick(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase) {
        int meleeDamage = OtherInformationTableHelper.getValue(infoDatabase, 5),
                criticalHit = OtherInformationTableHelper.getValue(infoDatabase, 6);
        Log.d("crit and meleedmg", String.format("%d, %d", meleeDamage, criticalHit));
        boolean isAllGood;
        if (isHaving(3, talentsDatabase)) {
            isAllGood = meleeDamage - 1 >= 0 && criticalHit + 5 <= 100;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        3));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        meleeDamage - 1,
                        OtherInformationDatabaseHelper.KEY_ID,
                        5));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        criticalHit + 5,
                        OtherInformationDatabaseHelper.KEY_ID,
                        6));
            }
        } else {
            isAllGood = criticalHit - 5 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        3));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        meleeDamage + 1,
                        OtherInformationDatabaseHelper.KEY_ID,
                        5));
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %s",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        criticalHit - 5,
                        OtherInformationDatabaseHelper.KEY_ID,
                        6));
            }
        }
        Log.d("crit and meleedmg", String.format("%d, %d", OtherInformationTableHelper.getValue(infoDatabase, 5), OtherInformationTableHelper.getValue(infoDatabase, 6)));
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingBull(SQLiteDatabase talentsDatabase, SQLiteDatabase
            characteristicsDatabase, SQLiteDatabase infoDatabase) {
        int strength = CharacteristicsTableHelper.getValue(characteristicsDatabase, 1),
                physique = CharacteristicsTableHelper.getValue(characteristicsDatabase, 2),
                ap = OtherInformationTableHelper.getValue(infoDatabase, 2);
        Log.d("value of ap, strength, physique", String.format("%d, %d,%d",
                OtherInformationTableHelper.getValue(infoDatabase, 2),
                strength, physique));
        boolean isAllGood;
        if (isHaving(2, talentsDatabase)) {
            isAllGood = strength - 1 >= 0 && physique - 1 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        2));
                if (strength >= 1) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            strength - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            1));
                }
                if (physique >= 1) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            physique - 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            2));
                }
                infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        OtherInformationDatabaseHelper.Table_Other_Info,
                        OtherInformationDatabaseHelper.KEY_Value,
                        ap + 2,
                        OtherInformationDatabaseHelper.KEY_ID,
                        2));
            }
        } else {
            isAllGood = strength + 1 <= 5 && physique + 1 <= 5;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        2));
                if (strength <= 4) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            strength + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            1));
                }
                if (physique <= 4) {
                    characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            CharacteristicsDatabaseHelper.Table_Characteristics,
                            CharacteristicsDatabaseHelper.KEY_Value,
                            physique + 1,
                            CharacteristicsDatabaseHelper.KEY_ID,
                            2));
                }
                if (ap >= 2) {
                    infoDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                            OtherInformationDatabaseHelper.Table_Other_Info,
                            OtherInformationDatabaseHelper.KEY_Value,
                            ap - 2,
                            OtherInformationDatabaseHelper.KEY_ID,
                            2));
                }
            }
        }
        Log.d("value of ap, strength, physique", String.format("%d, %d,%d",
                OtherInformationTableHelper.getValue(infoDatabase, 2),
                CharacteristicsTableHelper.getValue(characteristicsDatabase, 1),
                CharacteristicsTableHelper.getValue(characteristicsDatabase, 2)));
        return isAllGood;
    }

    @SuppressLint("DefaultLocale")
    public static boolean choosingSinger(SQLiteDatabase talentsDatabase, SQLiteDatabase
            characteristicsDatabase) {
        int value = CharacteristicsTableHelper.getValue(characteristicsDatabase, 7);
        boolean isAllGood;
        if (isHaving(1, talentsDatabase)) {
            isAllGood = value - 1 >= 0;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=0 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        1));
                characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value - 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        7));
            }
        } else {
            isAllGood = value + 1 <= 5;
            if (isAllGood) {
                talentsDatabase.execSQL(String.format("UPDATE %s SET %s=1 WHERE %s = %s",
                        TalentsDatabaseHelper.Table_Talents,
                        TalentsDatabaseHelper.KEY_Having,
                        TalentsDatabaseHelper.KEY_ID,
                        1));

                characteristicsDatabase.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value + 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        7));
            }
        }
        return isAllGood;
    }

    public static void settingStartingValuesInDatabase(SQLiteDatabase database, String[] names) {
        database.delete(TalentsDatabaseHelper.Table_Talents, null, null);
        ContentValues contentValues1 = new ContentValues(),
                contentValues2 = new ContentValues(), contentValues3 = new ContentValues(),
                contentValues4 = new ContentValues(), contentValues5 = new ContentValues(),
                contentValues6 = new ContentValues(), contentValues7 = new ContentValues();

        contentValues1.put(TalentsDatabaseHelper.KEY_ID, 1);
        contentValues2.put(TalentsDatabaseHelper.KEY_ID, 2);
        contentValues3.put(TalentsDatabaseHelper.KEY_ID, 3);
        contentValues4.put(TalentsDatabaseHelper.KEY_ID, 4);
        contentValues5.put(TalentsDatabaseHelper.KEY_ID, 5);
        contentValues6.put(TalentsDatabaseHelper.KEY_ID, 6);
        contentValues7.put(TalentsDatabaseHelper.KEY_ID, 7);

        contentValues1.put(TalentsDatabaseHelper.KEY_Name, names[0]);
        contentValues2.put(TalentsDatabaseHelper.KEY_Name, names[1]);
        contentValues3.put(TalentsDatabaseHelper.KEY_Name, names[2]);
        contentValues4.put(TalentsDatabaseHelper.KEY_Name, names[3]);
        contentValues5.put(TalentsDatabaseHelper.KEY_Name, names[4]);
        contentValues6.put(TalentsDatabaseHelper.KEY_Name, names[5]);
        contentValues7.put(TalentsDatabaseHelper.KEY_Name, names[6]);

        contentValues1.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues2.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues3.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues4.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues5.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues6.put(TalentsDatabaseHelper.KEY_Having, 0);
        contentValues7.put(TalentsDatabaseHelper.KEY_Having, 0);

        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues1);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues2);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues3);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues4);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues5);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues6);
        database.insert(TalentsDatabaseHelper.Table_Talents, null, contentValues7);
    }

    public static boolean isHaving(int ID, SQLiteDatabase database) {
        Cursor havingTalentCursor = database.query(TalentsDatabaseHelper.Table_Talents, null, null, null, null, null, null);
        havingTalentCursor.moveToFirst();
        int idColumnIndex = havingTalentCursor.getColumnIndex(TalentsDatabaseHelper.KEY_ID),
                havingColumnIndex = havingTalentCursor.getColumnIndex(TalentsDatabaseHelper.KEY_Having);
        boolean isHaving = false;
        do {
            if (havingTalentCursor.getInt(idColumnIndex) == ID) {
                isHaving = havingTalentCursor.getInt(havingColumnIndex) == 1;
                break;
            }
        } while (havingTalentCursor.moveToNext());
        havingTalentCursor.close();
        return isHaving;
    }*/
}
