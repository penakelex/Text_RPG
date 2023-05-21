package penakelex.textRPG.homeland.Map;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;

import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location_Def_Value;
import static penakelex.textRPG.homeland.Main.Constants.World;
import static penakelex.textRPG.homeland.Main.Constants.Starting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_1;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_2;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_3;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_4;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_5;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_6;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_7;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_8;
import penakelex.textRPG.homeland.Map.GlobalMapFragments.GlobalMapFragment_9;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityMapBinding;


public class Map extends MainActionParentActivity {
    private ActivityMapBinding binding;
    private SharedPreferences sharedPreferences;
    private final int Global_Map_Location_Def_Value = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        startLocation();
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 2).apply();
        binding.north.setOnClickListener(listener -> changingFieldNorth());
        binding.east.setOnClickListener(listener -> changingFieldEast());
        binding.south.setOnClickListener(listener -> changingFieldSouth());
        binding.west.setOnClickListener(listener -> changingFieldWest());
        binding.button.setOnClickListener(listener -> worldButtonPressed());
    }

    private void worldButtonPressed() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value)) {
            case 2:
                sharedPreferences.edit().putInt(World, 1).apply(); //Службный въезд
                break;
            case 12:
                sharedPreferences.edit().putInt(World, 2).apply();  //Гараж
                break;
            case 20:
                sharedPreferences.edit().putInt(World, 3).apply(); //Медпункт
                break;
            case 5:
            case 6:
                sharedPreferences.edit().putInt(World, 4).apply(); //Технические помещения
                break;
            case 15:
            case 24:
                sharedPreferences.edit().putInt(World, 5).apply(); //Столовая
                break;
            case 27:
                sharedPreferences.edit().putInt(World, 6).apply(); //Пост охраны
                break;
            case 43:
            case 35:
                sharedPreferences.edit().putInt(World, 7).apply(); //Тренировочный комплекс
                break;
            case 44:
            case 63:
            case 34:
                sharedPreferences.edit().putInt(World, 8).apply(); //Спальные районы
                break;
            case 42:
            case 51:
                sharedPreferences.edit().putInt(World, 10).apply(); //Технические помещения
                break;
            case 30:
                sharedPreferences.edit().putInt(World, 11).apply(); //Склад
                break;
            case 46:
            case 47:
                sharedPreferences.edit().putInt(World, 12).apply(); //Антены слева
                break;
            case 73:
                sharedPreferences.edit().putInt(World, 13).apply(); //Ракета
                break;
            case 57:
                sharedPreferences.edit().putInt(World, 14).apply(); //Диспетчерская
                break;
            case 67:
                sharedPreferences.edit().putInt(World, 15).apply(); //Антены справа
                break;
        }
        startActivity(new Intent(Map.this, DialogActivity.class));
        binding = null;
        finish();
    }

    private void changingFieldNorth() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_4()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            }
            break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_5()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_center));

            }
            break;
            case 3: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_6()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
            }
            break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 7).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_7()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_8()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 9).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_9()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
            }
            break;
            case 7:
            case 8:
            case 9:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that1), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void changingFieldSouth() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1:
            case 2:
            case 3:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that1), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 1).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_1()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_2()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 3).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_3()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            }
            break;
            case 7: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_4()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_5()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            }
            break;
            case 9: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(binding.containerForMapFragments.getId(), new GlobalMapFragment_6()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
            }
            break;
        }
    }

    private void changingFieldEast() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int container = binding.containerForMapFragments.getId();
        switch (sharedPreferences.getInt(Global_Map_Location, 1)) {
            case 1: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            }
            break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 3).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_3()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            }
            break;
            case 3:
            case 6:
            case 9:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that2), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_6()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            }
            break;
            case 7: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 9).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_9()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
            }
            break;
        }
    }

    private void changingFieldWest() {
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int container = binding.containerForMapFragments.getId();
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1:
            case 4:
            case 7:
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_do_that2), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 1).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_1()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            }
            break;
            case 3: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_4()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 7).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_7()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            }
            break;
            case 9: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            }
            break;
        }
    }

    private void startLocation() {
        int container = binding.containerForMapFragments.getId();
        sharedPreferences = getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Starting, true).apply();
        switch (sharedPreferences.getInt(Global_Map_Location, 1)) {
            case 1: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_1()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southwest));
            }
            break;
            case 2: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_south));
            }
            break;
            case 3: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_3()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_southeast));
            }
            break;
            case 4: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_4()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_west));
            }
            break;
            case 5: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_center));
            }
            break;
            case 6: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_6()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_east));
            }
            break;
            case 7: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_7()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northwest));
            }
            break;
            case 8: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_north));
            }
            break;
            case 9: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_9()).addToBackStack(null).commit();
                binding.currentLocation.setText(getResources().getString(R.string.current_location_northeast));
            }
            break;
        }
        setClickable();
    }

    private void setClickable() {
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