package penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingCharacteristicsBinding;

public class CharacteristicsTableHelper {
    private final CharacteristicsViewModel viewModel;

    public CharacteristicsTableHelper(CharacteristicsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @SuppressLint("DefaultLocale")
    public void raiseCharacteristic(SharedPreferences sharedPreferences, byte ID, FragmentStartingCharacteristicsBinding binding, Context context) {
        if (sharedPreferences.getInt(Characteristics_Points, 2) == 0) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.not_enough_characteristics_points), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            CharacteristicItem characteristic = viewModel.getCharacteristic(ID);
            if (characteristic.getValue() < 5) {
                viewModel.update((byte) (characteristic.getValue() + 1), ID);
                sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) - 1).apply();
            } else {
                Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.max_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }
        }
    }

    @SuppressLint("DefaultLocale")
    public void downgradeCharacteristic(SharedPreferences sharedPreferences, byte ID, FragmentStartingCharacteristicsBinding binding, Context context) {
        CharacteristicItem characteristic = viewModel.getCharacteristic(ID);
        if (characteristic.getValue() == 0) {
            Snackbar.make(binding.getRoot(), context.getResources().getString(R.string.min_value_of_characteristic), Snackbar.LENGTH_SHORT).setTextColor(context.getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            viewModel.update((byte) (characteristic.getValue() - 1), ID);
            sharedPreferences.edit().putInt(Characteristics_Points, sharedPreferences.getInt(Characteristics_Points, 2) + 1).apply();
        }
    }
}
