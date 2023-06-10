package penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingCharacteristicsBinding;

public class CharacteristicsTableHelper {
    private final CharacteristicsViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;

    public CharacteristicsTableHelper(CharacteristicsViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @SuppressLint("DefaultLocale")
    public void raiseCharacteristic(SharedPreferences sharedPreferences, byte ID, FragmentStartingCharacteristicsBinding binding, Context context) {
        if (sharedPreferences.getInt(Characteristics_Points, 2) == 0) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.not_enough_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            LiveData<CharacteristicItem> liveData = viewModel.getCharacteristic(ID);
            liveData.observe(lifecycleOwner, characteristicItem -> {
                liveData.removeObservers(lifecycleOwner);
                if (characteristicItem.getValue() < 5) {
                    viewModel.update((byte) (characteristicItem.getValue() + 1), ID);
                    sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) - 1).apply();
                    binding.characteristicsPoints.setText(String.format("%s %d", context.getResources().getString(R.string.rest_of_characteristics_points), sharedPreferences.getInt(Characteristics_Points, 2)));
                } else {
                    Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.max_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    public void downgradeCharacteristic(SharedPreferences sharedPreferences, byte ID, FragmentStartingCharacteristicsBinding binding, Context context) {
        LiveData<CharacteristicItem> liveData = viewModel.getCharacteristic(ID);
        liveData.observe(lifecycleOwner, characteristicItem -> {
            liveData.removeObservers(lifecycleOwner);
            if (characteristicItem.getValue() == 0) {
                Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.min_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            } else {
                viewModel.update((byte) (characteristicItem.getValue() - 1), ID);
                sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) + 1).apply();
                binding.characteristicsPoints.setText(String.format("%s %d", context.getResources().getString(R.string.rest_of_characteristics_points), sharedPreferences.getInt(Characteristics_Points, 2)));
            }
        });
    }

    /*public static ArrayList<StartingCharacteristicInformation> getStartingCharacteristicsInformation(SQLiteDatabase database) {
        ArrayList<StartingCharacteristicInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Name),
                valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            arrayList.add(new StartingCharacteristicInformation(cursor.getString(nameColumnIndex), cursor.getInt(valueColumnIndex)));
        } while (cursor.moveToNext());
        cursor.close();
        return arrayList;
    }

    public static ArrayList<CharacteristicsInformation> getCharacteristicsInformation(SQLiteDatabase database) {
        ArrayList<CharacteristicsInformation> arrayList = new ArrayList<>();
        Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        int nameColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Name),
                valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            arrayList.add(new CharacteristicsInformation(cursor.getString(nameColumnIndex), cursor.getInt(valueColumnIndex)));
        } while (cursor.moveToNext());
        cursor.close();
        return arrayList;
    }

    public static int[] getCharacteristicsValues(SQLiteDatabase database) {
        int valueColumnIndex;
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value);
        cursor.moveToFirst();
        do {
            list.add(cursor.getInt(valueColumnIndex));
        } while (cursor.moveToNext());
        cursor.close();
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }

    public static int getValue(SQLiteDatabase database, int ID) {
        Cursor afterCursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
        int newIdColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                newValueColumnIndex = afterCursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                newValue = 0;
        afterCursor.moveToFirst();
        do {
            if (afterCursor.getInt(newIdColumnIndex) == ID) {
                newValue = afterCursor.getInt(newValueColumnIndex);
                break;
            }
        } while (afterCursor.moveToNext());
        afterCursor.close();
        return newValue;
    }

    @SuppressLint("DefaultLocale")
    public static int raiseCharacteristic(SQLiteDatabase database, SharedPreferences sharedPreferences, int ID) {
        if (ID != 0) {
            Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
            int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                    valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                    value = 0;
            cursor.moveToFirst();
            do {
                if (cursor.getInt(idColumnIndex) == ID) {
                    value = cursor.getInt(valueColumnIndex);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (value + 1 <= 5 && sharedPreferences.getInt(Constants.Characteristics_Points, 2) - 1 >= 0) {
                database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value + 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        ID));
                return 1;
            } else {
                if (value + 1 > 5) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else return 0;
    }

    @SuppressLint("DefaultLocale")
    public static int downgradeCharacteristic(SQLiteDatabase database, int ID) {
        if (ID != 0) {
            Cursor cursor = database.query(CharacteristicsDatabaseHelper.Table_Characteristics, null, null, null, null, null, null);
            cursor.moveToFirst();
            int idColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_ID),
                    valueColumnIndex = cursor.getColumnIndex(CharacteristicsDatabaseHelper.KEY_Value),
                    value = 0;
            do {
                if (cursor.getInt(idColumnIndex) == ID) {
                    value = cursor.getInt(valueColumnIndex);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (value - 1 >= 0) {
                database.execSQL(String.format("UPDATE %s SET %s=%d WHERE %s = %d",
                        CharacteristicsDatabaseHelper.Table_Characteristics,
                        CharacteristicsDatabaseHelper.KEY_Value,
                        value - 1,
                        CharacteristicsDatabaseHelper.KEY_ID,
                        ID));
                return 1;
            } else return 2;
        } else return 3;
    }

    public static void settingStartValuesInDatabase(SQLiteDatabase database, String[] names) {
        database.delete(CharacteristicsDatabaseHelper.Table_Characteristics, null, null);

        ContentValues contentValuesStrength = new ContentValues(),
                contentValuesPhysique = new ContentValues(),
                contentValuesDexterity = new ContentValues(),
                contentValuesMentality = new ContentValues(),
                contentValuesLuckiness = new ContentValues(),
                contentValuesWatchfulness = new ContentValues(),
                contentValuesAttractiveness = new ContentValues();

        contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_ID, 1);
        contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_Name, names[0]);
        contentValuesStrength.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_ID, 2);
        contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_Name, names[1]);
        contentValuesPhysique.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_ID, 3);
        contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_Name, names[2]);
        contentValuesDexterity.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_ID, 4);
        contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_Name, names[3]);
        contentValuesMentality.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_ID, 5);
        contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_Name, names[4]);
        contentValuesLuckiness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_ID, 6);
        contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_Name, names[5]);
        contentValuesWatchfulness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_ID, 7);
        contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_Name, names[6]);
        contentValuesAttractiveness.put(CharacteristicsDatabaseHelper.KEY_Value, 2);

        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesStrength);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesPhysique);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesDexterity);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesMentality);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesLuckiness);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesWatchfulness);
        database.insert(CharacteristicsDatabaseHelper.Table_Characteristics, null, contentValuesAttractiveness);
    }*/
}
