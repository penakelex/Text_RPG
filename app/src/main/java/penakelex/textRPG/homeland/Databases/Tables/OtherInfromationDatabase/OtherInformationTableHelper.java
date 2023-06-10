package penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
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
    private final LifecycleOwner lifecycleOwner;

    public OtherInformationTableHelper(OtherInformationViewModel otherInformationViewModel, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, LifecycleOwner lifecycleOwner) {
        this.otherInformationViewModel = otherInformationViewModel;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public OtherInformationTableHelper(Activity activity) {
        this.otherInformationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(OtherInformationViewModel.class);
        this.otherInformationViewModel.initiate(activity.getApplication());
        this.characteristicsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(CharacteristicsViewModel.class);
        this.characteristicsViewModel.initiate(activity.getApplication());
        this.skillsViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SkillsViewModel.class);
        this.skillsViewModel.initiate(activity.getApplication());
        this.lifecycleOwner = (LifecycleOwner) activity;
    }

    public void updateInformation() {
        LiveData<List<CharacteristicItem>> characteristics = characteristicsViewModel.getAllCharacteristics();
        characteristics.observe(lifecycleOwner, characteristicItems -> {
            characteristics.removeObservers(lifecycleOwner);
            LiveData<List<SkillsItem>> skills = skillsViewModel.getAllSkills();
            skills.observe(lifecycleOwner, skillsItems -> {
                skills.removeObservers(lifecycleOwner);
                otherInformationViewModel.updateInformation(0, (byte) 1);
                otherInformationViewModel.updateInformation(Math.max(characteristicItems.get(2).getValue() + characteristicItems.get(1).getValue(), 5), (byte) 2);
                otherInformationViewModel.updateInformation(characteristicItems.get(0).getValue() * 10 + characteristicItems.get(2).getValue() * 3 + 20, (byte) 3);
                otherInformationViewModel.updateInformation(characteristicItems.get(0).getValue() * 2000 + characteristicItems.get(2).getValue() * 1000 + 10000, (byte) 4);
                otherInformationViewModel.updateInformation(characteristicItems.get(0).getValue() + characteristicItems.get(2).getValue() / 2 + skillsItems.get(2).getValue() / 20, (byte) 5);
                otherInformationViewModel.updateInformation(characteristicItems.get(4).getValue() * 2 + characteristicItems.get(2).getValue() + characteristicItems.get(5).getValue() / 2 + skillsItems.get(6).getValue() / 25, (byte) 6);
                otherInformationViewModel.updateInformation(characteristicItems.get(1).getValue() * 2 + skillsItems.get(5).getValue() / 2, (byte) 7);
            });
        });
    }
    /*contentValues1.put(OtherInformationDatabaseHelper.KEY_Value, 0);
        Math.max(characteristics[2] + characteristics[1], 5));
        characteristics[0] * 10 + characteristics[2] * 3 + 20);
        characteristics[0] * 2000 + characteristics[2] * 1000 + 10000); // В кубических сантиметрах
        characteristics[0] + characteristics[2] / 2 + skills[2] / 20);
        characteristics[4] * 2 + characteristics[2] + characteristics[5] / 2 + skills[6] / 25);
        characteristics[1] * 2 + skills[5] / 2);

    public static ArrayList<OtherInformationInformation> getInformation(SQLiteDatabase database) {
        ArrayList<OtherInformationInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Name),
                valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            arrayList.add(new OtherInformationInformation(cursor.getString(nameColumnIndex), cursor.getInt(valueColumnIndex)));
        } while (cursor.moveToNext());
        cursor.close();
        arrayList.remove(arrayList.size() - 1);
        return (ArrayList<OtherInformationInformation>) arrayList.clone();
    }

    public static ArrayList<StartingOtherInformationInformation> getStartingInformation(SQLiteDatabase database) {
        ArrayList<StartingOtherInformationInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Name),
                valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        cursor.moveToNext();
        do {
            arrayList.add(new StartingOtherInformationInformation(cursor.getString(nameColumnIndex), cursor.getInt(valueColumnIndex)));
        } while (cursor.moveToNext());
        cursor.close();
        return arrayList;
    }

    public static int getValue(SQLiteDatabase database, int ID) {
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int idColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_ID),
                valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value),
                value = 0;
        cursor.moveToFirst();
        do {
            if (cursor.getInt(idColumnIndex) == ID) {
                value = cursor.getInt(valueColumnIndex);
                break;
            }
        } while (cursor.moveToNext());
        cursor.close();
        return value;
    }

    public static int[] getValues(SQLiteDatabase database) {
        Cursor cursor = database.query(OtherInformationDatabaseHelper.Table_Other_Info, null, null, null, null, null, null);
        int valueColumnIndex = cursor.getColumnIndex(OtherInformationDatabaseHelper.KEY_Value);
        ArrayList<Integer> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        do {
            arrayList.add(cursor.getInt(valueColumnIndex));
        } while (cursor.moveToNext());
        cursor.close();
        int[] values = new int[arrayList.size()];
        for (int i = 0; i < values.length; i++) values[i] = arrayList.get(i);
        return values;
    }

    public static void settingStartingValuesInInformationDatabase(SQLiteDatabase infoDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase, String[] names) {
        infoDatabase.delete(OtherInformationDatabaseHelper.Table_Other_Info, null, null);
        int[] characteristics = CharacteristicsTableHelper.getCharacteristicsValues(characteristicsDatabase);
        int[] skills = SkillTableHelper.getSkillValues(skillsDatabase);
        ContentValues contentValues1 = new ContentValues(), contentValues2 = new ContentValues(),
                contentValues3 = new ContentValues(), contentValues4 = new ContentValues(),
                contentValues5 = new ContentValues(), contentValues6 = new ContentValues(),
                contentValues7 = new ContentValues();

        contentValues1.put(OtherInformationDatabaseHelper.KEY_ID, 1);
        contentValues2.put(OtherInformationDatabaseHelper.KEY_ID, 2);
        contentValues3.put(OtherInformationDatabaseHelper.KEY_ID, 3);
        contentValues4.put(OtherInformationDatabaseHelper.KEY_ID, 4);
        contentValues5.put(OtherInformationDatabaseHelper.KEY_ID, 5);
        contentValues6.put(OtherInformationDatabaseHelper.KEY_ID, 6);
        contentValues7.put(OtherInformationDatabaseHelper.KEY_ID, 7);

        contentValues1.put(OtherInformationDatabaseHelper.KEY_Name, names[0]);
        contentValues2.put(OtherInformationDatabaseHelper.KEY_Name, names[1]);
        contentValues3.put(OtherInformationDatabaseHelper.KEY_Name, names[2]);
        contentValues4.put(OtherInformationDatabaseHelper.KEY_Name, names[3]);
        contentValues5.put(OtherInformationDatabaseHelper.KEY_Name, names[4]);
        contentValues6.put(OtherInformationDatabaseHelper.KEY_Name, names[5]);
        contentValues7.put(OtherInformationDatabaseHelper.KEY_Name, names[6]);

        contentValues1.put(OtherInformationDatabaseHelper.KEY_Value, 0);
        contentValues2.put(OtherInformationDatabaseHelper.KEY_Value, Math.max(characteristics[2] + characteristics[1], 5));
        contentValues3.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 10 + characteristics[2] * 3 + 20);
        contentValues4.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] * 2000 + characteristics[2] * 1000 + 10000); // В кубических сантиметрах
        contentValues5.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[0] + characteristics[2] / 2 + skills[2] / 20);
        contentValues6.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[4] * 2 + characteristics[2] + characteristics[5] / 2 + skills[6] / 25);
        contentValues7.put(OtherInformationDatabaseHelper.KEY_Value, characteristics[1] * 2 + skills[5] / 2);

        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues1);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues2);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues3);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues4);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues5);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues6);
        infoDatabase.insert(OtherInformationDatabaseHelper.Table_Other_Info, null, contentValues7);
    }*/
}
