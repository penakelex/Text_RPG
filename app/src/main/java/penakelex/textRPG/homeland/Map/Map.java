package penakelex.textRPG.homeland.Map;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location_Def_Value;
import static penakelex.textRPG.homeland.Main.Constants.Starting;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.Map.MapFragment.MapFragment;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityMapBinding;
/** Map
 *      Активность с картой
 * */
public class Map extends MainActionParentActivity {
    private ActivityMapBinding binding;
    private SharedPreferences sharedPreferences;
    private final int Global_Map_Location_Def_Value = 1;
    private OnMapClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        MapFragment fragment = new MapFragment();
        assert binding.containerForMapFragment != null;
        getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragment.getId(), fragment).commit();
        //Реализация интерфейса из фрагмента
        clickListener = fragment.getClickListener();
        setContentView(binding.getRoot());
        toolbar();
        //Установка стартовой локации
        startLocation();
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(Current_Activity, 2).apply();
        buttons();
    }

    /** buttons - процедура
     *      Содержит слушателей кнопок
     * */
    private void buttons() {
        binding.north.setOnClickListener(listener -> changingFieldNorth());
        binding.east.setOnClickListener(listener -> changingFieldEast());
        binding.south.setOnClickListener(listener -> changingFieldSouth());
        binding.west.setOnClickListener(listener -> changingFieldWest());
        binding.button.setOnClickListener(listener -> worldButtonPressed());
    }

    /** toolbar - процедура
     *      Создание и управление виджетом
     * */
    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
    }

    /** worldButtonPressed - процедура
     *      Слушатель на нажатие кнопки "Далее"
     * */
    private void worldButtonPressed() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        switch (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value)) {
            case 2 -> {
                if (sharedPreferences.getInt(ID_Dialog, 1) == 100) {
                    sharedPreferences.edit().putInt(ID_Dialog, 15).apply();
                    goingToDialog();
                }
            } //Служебный въезд
            case 20 -> {
                if (sharedPreferences.getInt(ID_Dialog, 1) == 100) {
                    sharedPreferences.edit().putInt(ID_Dialog, 16).apply();
                }
                goingToDialog();
            } //Медпункт
            case 15, 24 -> {
                if (sharedPreferences.getInt(ID_Dialog, 1) == 100) {
                    sharedPreferences.edit().putInt(ID_Dialog, 17).apply();
                }
                goingToDialog();
            } //Столовая
            case 43, 35 -> {
                if (sharedPreferences.getInt(ID_Dialog, 1) == 100) {
                    sharedPreferences.edit().putInt(ID_Dialog, 18).apply();
                }
                goingToDialog();
            } //Тренировочный комплекс
            case 44, 63, 34 -> {
                if (sharedPreferences.getInt(ID_Dialog, 1) == 100) {
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_dont_see_anything_intresting), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                } else goingToDialog();
            } //Спальные районы
            default -> Snackbar.make(binding.getRoot(), getResources().getString(R.string.u_dont_see_anything_intresting), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            //Всё прочее
        }
    }

    /** goingToDialog - процедура
     *      Переход на активность с диалогами
     * */
    private void goingToDialog() {
        startActivity(new Intent(Map.this, DialogActivity.class));
        binding = null;
        finish();
    }

    /** changingFieldNorth - процедура
     *      Переход на локацию севернее, чем текущая
     * */
    private void changingFieldNorth() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            case 2 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            case 3 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
            case 4 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            case 5 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            case 6 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
            case 7, 8, 9 ->
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that1), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
        //Оповещение фрагмента об изменении
        clickListener.goingNorth();
    }

    /** changingFieldSouth - процедура
     *      Переход на локацию южнее, чем текущая
     * */
    private void changingFieldSouth() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1, 2, 3 ->
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that1), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            case 4 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            case 5 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            case 6 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            case 7 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            case 8 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            case 9 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
        }
        //Оповещение фрагмента об изменении
        clickListener.goingSouth();
    }

    /** changingFieldEast - процедура
     *      Переход на локацию восточнее, чем текущая
     * */
    private void changingFieldEast() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        switch (sharedPreferences.getInt(Global_Map_Location, 1)) {
            case 1 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            case 2 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            case 3, 6, 9 ->
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that2), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            case 4 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            case 5 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            case 7 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            case 8 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
        }
        //Оповещение фрагмента об изменении
        clickListener.goingEast();
    }

    /** changingFieldWest - процедура
     *      Переход на локацию западнее, чем текущая
     * */
    private void changingFieldWest() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1, 4, 7 ->
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that2), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            case 2 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            case 3 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            case 5 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            case 6 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            case 8 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            case 9 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
        }
        //Оповещение фрагмента об изменении
        clickListener.goingWest();
    }

    /** startLocation - процедура
     *      Начальная локация
     * */
    private void startLocation() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(Starting, true).apply();
        byte location = (byte) sharedPreferences.getInt(Global_Map_Location, 1);
        switch (location) {
            case 1 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            case 2 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            case 3 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            case 4 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            case 5 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            case 6 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
            case 7 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            case 8 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            case 9 ->
                    binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
        }
        //Оповещение фрагмента о текущем положении
        clickListener.startLocation(location);
        //Напоминание
        setClickable();
    }

    /** setClickable - процедура
     *      Напоминание игроку
     * */
    private void setClickable() {
        if (sharedPreferences.getBoolean(Static_Position, false))
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.press_world), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}