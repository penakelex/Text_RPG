package penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Is_All_Good;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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

public class TalentsTableHelper {
    private final CharacteristicsViewModel characteristicsViewModel;
    private final SkillsViewModel skillsViewModel;
    private final TalentsViewModel talentsViewModel;
    private final OtherInformationViewModel otherInformationViewModel;
    private final LifecycleOwner lifecycleOwner;

    public TalentsTableHelper(CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, TalentsViewModel talentsViewModel, OtherInformationViewModel otherInformationViewModel, LifecycleOwner lifecycleOwner) {
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.talentsViewModel = talentsViewModel;
        this.otherInformationViewModel = otherInformationViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void choosingTalent(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        if (sharedPreferences.getInt(Talents_Points, 2) == 0) {
            Snackbar.make(view, context.getResources().getString(R.string.not_enough_talents_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            switch (ID) {
                case 1 -> choosingSinger(sharedPreferences, ID, view, context);
                case 2 -> choosingBull(sharedPreferences, ID, view, context);
                case 3 -> choosingStrongKick(sharedPreferences, ID);
                case 4 -> choosingExperienced(sharedPreferences, ID, view, context);
                case 5 -> choosingTrained(sharedPreferences, ID, view, context);
                case 6 -> choosingHeavyweight(sharedPreferences, ID, view, context);
                case 7 -> choosingKindOne(sharedPreferences, ID, view, context);
                default -> Snackbar.make(view, context.getResources().getString(R.string.have_not_chosen_talent), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        }
    }

    private void choosingKindOne(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<List<SkillsItem>> skills = skillsViewModel.getAllSkills();
            skills.observe(lifecycleOwner, new Observer<>() {
                @Override
                public void onChanged(List<SkillsItem> skillsItems) {
                    skills.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        if (skillsAreReady(skillsItems, true)) {
                            updateSkillsValues(skillsItems, true);
                            sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                        } else {
                            Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                        }
                    } else {
                        if (skillsAreReady(skillsItems, false)) {
                            updateSkillsValues(skillsItems, false);
                            sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                        } else {
                            Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                        }
                    }
                    talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
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
            });
        });
    }

    private void choosingHeavyweight(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<OtherInformationItem> carryWeight = otherInformationViewModel.getOtherInformationItemByID((byte) 3);
            carryWeight.observe(lifecycleOwner, item -> {
                carryWeight.removeObservers(lifecycleOwner);
                if (talentItem.isHaving()) {
                    if (item.getValue() - 20 > 0) {
                        otherInformationViewModel.updateInformation(item.getValue() - 20, item.getID());
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                    } else {
                        Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                    }
                } else {
                    otherInformationViewModel.updateInformation(item.getValue() + 20, item.getID());
                    sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                }
            });
            talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
        });
    }

    private void choosingTrained(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<List<CharacteristicItem>> characteristics = characteristicsViewModel.getAllCharacteristics();
            LiveData<List<SkillsItem>> skills = skillsViewModel.getAllSkills();
            characteristics.observe(lifecycleOwner, characteristicItems -> {
                characteristics.removeObservers(lifecycleOwner);
                boolean isAllGood = true;
                if (talentItem.isHaving()) {
                    for (CharacteristicItem characteristic : characteristicItems) {
                        if (characteristic.getValue() == 0) {
                            isAllGood = false;
                            break;
                        }
                    }
                } else {
                    for (CharacteristicItem characteristic : characteristicItems) {
                        if (characteristic.getValue() == 5) {
                            isAllGood = false;
                            break;
                        }
                    }
                }
                sharedPreferences.edit().putBoolean(Is_All_Good, isAllGood).apply();
            });
            if (sharedPreferences.getBoolean(Is_All_Good, true)) {
                skills.observe(lifecycleOwner, skillsItems -> {
                    skills.removeObservers(lifecycleOwner);
                    boolean isAllGood = true;
                    if (talentItem.isHaving()) {
                        for (SkillsItem skill : skillsItems) {
                            if (skill.getValue() + 10 > 100) {
                                isAllGood = false;
                                break;
                            }
                        }
                    } else {
                        for (SkillsItem skill : skillsItems) {
                            if (skill.getValue() - 10 < 0) {
                                isAllGood = false;
                                break;
                            }
                        }
                    }
                    sharedPreferences.edit().putBoolean(Is_All_Good, isAllGood).apply();
                });
            }
            if (sharedPreferences.getBoolean(Is_All_Good, true)) {
                characteristics.observe(lifecycleOwner, characteristicItems -> {
                    characteristics.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        for (CharacteristicItem characteristic : characteristicItems) {
                            characteristicsViewModel.update((byte) (characteristic.getValue() - 1), characteristic.getID());
                        }
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                    } else {
                        for (CharacteristicItem characteristic : characteristicItems) {
                            characteristicsViewModel.update((byte) (characteristic.getValue() + 1), characteristic.getID());
                        }
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                    }
                });
                skills.observe(lifecycleOwner, skillsItems -> {
                    skills.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        for (SkillsItem skill : skillsItems) {
                            skillsViewModel.updateValue((byte) (skill.getValue() + 10), skill.getID());
                        }
                    } else {
                        for (SkillsItem skill : skillsItems) {
                            skillsViewModel.updateValue((byte) (skill.getValue() - 10), skill.getID());
                        }
                    }
                });
                talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        });
    }

    private void choosingExperienced(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<List<SkillsItem>> liveDataSkills = skillsViewModel.getAllSkills();
            liveDataSkills.observe(lifecycleOwner, skillsItems -> {
                liveDataSkills.removeObservers(lifecycleOwner);
                boolean isAllGood = true;
                if (talentItem.isHaving()) {
                    for (SkillsItem skillsItem : skillsItems) {
                        if (skillsItem.getValue() - 10 < 0) {
                            isAllGood = false;
                            break;
                        }
                    }
                } else {
                    for (SkillsItem skillsItem : skillsItems) {
                        if (skillsItem.getValue() + 10 > 100) {
                            isAllGood = false;
                            break;
                        }
                    }
                }
                sharedPreferences.edit().putBoolean(Is_All_Good, isAllGood).apply();
            });
            if (sharedPreferences.getBoolean(Is_All_Good, true)) {
                liveDataSkills.observe(lifecycleOwner, skillsItems -> {
                    liveDataSkills.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        for (SkillsItem skillsItem : skillsItems) {
                            skillsViewModel.updateValue((byte) (skillsItem.getValue() - 10), skillsItem.getID());
                        }
                    } else {
                        for (SkillsItem skillsItem : skillsItems) {
                            skillsViewModel.updateValue((byte) (skillsItem.getValue() + 10), skillsItem.getID());
                        }
                    }
                });
                talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        });
    }

    private void choosingStrongKick(SharedPreferences sharedPreferences, byte ID) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<OtherInformationItem> meleeDamage = otherInformationViewModel.getOtherInformationItemByID((byte) 5),
                    critical = otherInformationViewModel.getOtherInformationItemByID((byte) 6);
            meleeDamage.observe(lifecycleOwner, item -> {
                meleeDamage.removeObservers(lifecycleOwner);
                if (talentItem.isHaving()) {
                    otherInformationViewModel.updateInformation(item.getValue() - 1, (byte) 5);
                } else {
                    otherInformationViewModel.updateInformation(item.getValue() + 1, (byte) 5);
                }
            });
            critical.observe(lifecycleOwner, item -> {
                critical.removeObservers(lifecycleOwner);
                if (talentItem.isHaving()) {
                    otherInformationViewModel.updateInformation(item.getValue() + 5, (byte) 6);
                    sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                } else {
                    otherInformationViewModel.updateInformation(item.getValue() - 5, (byte) 6);
                    sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                }
            });
            talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
        });
    }

    private void choosingBull(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<OtherInformationItem> ap = otherInformationViewModel.getOtherInformationItemByID((byte) 2);
            LiveData<CharacteristicItem> strength = characteristicsViewModel.getCharacteristic((byte) 1),
                    physique = characteristicsViewModel.getCharacteristic((byte) 2);
            strength.observe(lifecycleOwner, item -> {
                strength.removeObservers(lifecycleOwner);
                if (talentItem.isHaving()) {
                    sharedPreferences.edit().putBoolean(Is_All_Good, item.getValue() > 0).apply();
                } else {
                    sharedPreferences.edit().putBoolean(Is_All_Good, item.getValue() < 5).apply();
                }
            });
            if (sharedPreferences.getBoolean(Is_All_Good, true)) {
                physique.observe(lifecycleOwner, item -> {
                    physique.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        sharedPreferences.edit().putBoolean(Is_All_Good, item.getValue() > 0).apply();
                    } else {
                        sharedPreferences.edit().putBoolean(Is_All_Good, item.getValue() < 5).apply();
                    }
                });
            }
            if (sharedPreferences.getBoolean(Is_All_Good, true)) {
                strength.observe(lifecycleOwner, item -> {
                    strength.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        characteristicsViewModel.update((byte) (item.getValue() - 1), item.getID());
                    } else {
                        characteristicsViewModel.update((byte) (item.getValue() + 1), item.getID());
                    }
                });
                physique.observe(lifecycleOwner, item -> {
                    physique.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        characteristicsViewModel.update((byte) (item.getValue() - 1), item.getID());
                    } else {
                        characteristicsViewModel.update((byte) (item.getValue() + 1), item.getID());
                    }
                });
                ap.observe(lifecycleOwner, otherInformationItem -> {
                    ap.removeObservers(lifecycleOwner);
                    if (talentItem.isHaving()) {
                        otherInformationViewModel.updateInformation(otherInformationItem.getValue() - 2, (byte) 2);
                    } else {
                        otherInformationViewModel.updateInformation(otherInformationItem.getValue() + 2, (byte) 2);
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                    }
                });
                talentsViewModel.changeIsHaving(!talentItem.isHaving(), ID);
            } else {
                Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        });
    }

    private void choosingSinger(SharedPreferences sharedPreferences, byte ID, View view, Context context) {
        LiveData<TalentItem> talent = talentsViewModel.getTalent(ID);
        talent.observe(lifecycleOwner, talentItem -> {
            talent.removeObservers(lifecycleOwner);
            LiveData<CharacteristicItem> characteristic = characteristicsViewModel.getCharacteristic((byte) 7);
            characteristic.observe(lifecycleOwner, characteristicItem -> {
                if (talentItem.isHaving()) {
                    if (characteristicItem.getValue() > 0) {
                        characteristicsViewModel.update((byte) (characteristicItem.getValue() - 1), (byte) 7);
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                    } else {
                        Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                    }
                } else {
                    if (characteristicItem.getValue() < 5) {
                        characteristicsViewModel.update((byte) (characteristicItem.getValue() + 1), (byte) 7);
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                    } else {
                        Snackbar.make(view, context.getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                    }
                }
            });
            talentsViewModel.changeIsHaving(!talentItem.isHaving(), talentItem.getID());
        });
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
