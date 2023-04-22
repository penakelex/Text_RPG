package com.example.textrpg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_1;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_2;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_3;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_4;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_5;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_6;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_7;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_8;
import com.example.textrpg.GlobalMapFragments.GlobalMapFragment_9;
import com.example.textrpg.R;
import com.example.textrpg.databinding.ActivityMapBinding;

//TODO: Доделать фрагменты, добавить во фрагменты про текущее местоположение(сейчас оно не работает нормально)
//TODO: Сделать через базу данных проверку был ли уже в этом месте персонаж, также не показывать круги, если не был
//TODO: Сделать вывод через binding активности были ли уже здесь или нет
public class Map extends AppCompatActivity {
    ActivityMapBinding activityMapBinding;
    SharedPreferences sharedPreferences;
    private final String Global_Map_Location = "Global Map Location", Starting = "Starting", Going_To_North = "Пришли на север карты.", Going_To_South = "Пришли на юг карты.", Going_To_West = "Пришли на запад карты.", Going_To_East = "Пришли на восток карты.", Can_Not_Do_That_1 = "Не получится...", Can_Not_Do_That_2 = "Слишком далеко...", Going_To_Northeast = "Пришли на северо-восток карты.", Going_To_Northwest = "Пришли на северо-запад карты.", Going_To_Southwest = "Пришли на юго-запад карты.", Going_To_Southeast = "Пришли на юго-восток карты.", Going_To_Center = "Пришли в центр карты.", Not_Going_Yet = "Пока ещё никуда не идём...", Current_Locaton_Southwest = "Текущее местоположение - юго-запад карты.", Current_Locaton_Southeast = "Текущее местоположение - юго-восток карты.", Current_Locaton_South = "Текущее местоположение - юг карты.", Current_Locaton_West = "Текущее местоположение - запад карты.", Current_Locaton_Center = "Текущее местоположение - центр карты.", Current_Locaton_East = "Текущее местоположение - восток карты.", Current_Locaton_Northwest = "Текущее местоположение - северо-запад карты.", Current_Locaton_North = "Текущее местоположение - север карты.", Current_Locaton_Northeast = "Текущее местоположение - северо-восток карты.";
    private final int Global_Map_Location_Def_Value = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(activityMapBinding.getRoot());
        creatingField();
        startLocation(activityMapBinding);
        activityMapBinding.north.setOnClickListener(listener -> changingFieldNorth(activityMapBinding));
        activityMapBinding.east.setOnClickListener(listener -> changingFieldEast(activityMapBinding));
        activityMapBinding.south.setOnClickListener(listener -> changingFieldSouth(activityMapBinding));
        activityMapBinding.west.setOnClickListener(listener -> changingFieldWest(activityMapBinding));
    }

    private void changingFieldNorth(ActivityMapBinding activityMapBinding) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_4()).commit();
                activityMapBinding.information.setText(Going_To_West);
                activityMapBinding.currentLocation.setText(Current_Locaton_West);
            }
            break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_5()).commit();
                activityMapBinding.information.setText(Going_To_Center);
                activityMapBinding.currentLocation.setText(Current_Locaton_Center);
            }
            break;
            case 3: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_6()).commit();
                activityMapBinding.information.setText(Going_To_East);
                activityMapBinding.currentLocation.setText(Current_Locaton_East);
            }
            break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 7).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_7()).commit();
                activityMapBinding.information.setText(Going_To_Northwest);
                activityMapBinding.currentLocation.setText(Current_Locaton_Northwest);
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_8()).commit();
                activityMapBinding.information.setText(Going_To_North);
                activityMapBinding.currentLocation.setText(Current_Locaton_North);
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 9).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_9()).commit();
                activityMapBinding.information.setText(Going_To_Northeast);
                activityMapBinding.currentLocation.setText(Current_Locaton_Northeast);
            }
            break;
            case 7:
            case 8:
            case 9:
                activityMapBinding.information.setText(Can_Not_Do_That_1);
        }
    }

    private void changingFieldSouth(ActivityMapBinding activityMapBinding) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1:
            case 2:
            case 3:
                activityMapBinding.information.setText(Can_Not_Do_That_1);
                break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 1).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_1()).commit();
                activityMapBinding.information.setText(Going_To_Southwest);
                activityMapBinding.currentLocation.setText(Current_Locaton_Southwest);
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_2()).commit();
                activityMapBinding.information.setText(Going_To_South);
                activityMapBinding.currentLocation.setText(Current_Locaton_South);
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 3).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_3()).commit();
                activityMapBinding.information.setText(Going_To_Southeast);
                activityMapBinding.currentLocation.setText(Current_Locaton_Southeast);
            }
            break;
            case 7: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_4()).commit();
                activityMapBinding.information.setText(Going_To_West);
                activityMapBinding.currentLocation.setText(Current_Locaton_West);
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_5()).commit();
                activityMapBinding.information.setText(Going_To_Center);
                activityMapBinding.currentLocation.setText(Current_Locaton_Center);
            }
            break;
            case 9: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new GlobalMapFragment_6()).commit();
                activityMapBinding.information.setText(Going_To_East);
                activityMapBinding.currentLocation.setText(Current_Locaton_East);
            }
            break;
        }
    }

    private void changingFieldEast(ActivityMapBinding activityMapBinding) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        int container = R.id.linearLayout;
        switch (sharedPreferences.getInt(Global_Map_Location, 1)) {
            case 1: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).commit();
                activityMapBinding.information.setText(Going_To_South);
                activityMapBinding.currentLocation.setText(Current_Locaton_South);
            }
            break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 3).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_3()).commit();
                activityMapBinding.information.setText(Going_To_Southeast);
                activityMapBinding.currentLocation.setText(Current_Locaton_Southeast);
            }
            break;
            case 3:
            case 6:
            case 9:
                activityMapBinding.information.setText(Can_Not_Do_That_2);
                break;
            case 4: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).commit();
                activityMapBinding.information.setText(Going_To_West);
                activityMapBinding.currentLocation.setText(Current_Locaton_West);
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 6).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_6()).commit();
                activityMapBinding.information.setText(Going_To_Center);
                activityMapBinding.currentLocation.setText(Current_Locaton_Center);
            }
            break;
            case 7: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).commit();
                activityMapBinding.information.setText(Going_To_North);
                activityMapBinding.currentLocation.setText(Current_Locaton_North);
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 9).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_9()).commit();
                activityMapBinding.information.setText(Going_To_Northeast);
                activityMapBinding.currentLocation.setText(Current_Locaton_Northeast);
            }
            break;
        }
    }

    private void changingFieldWest(ActivityMapBinding activityMapBinding) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        int container = R.id.linearLayout;
        switch (sharedPreferences.getInt(Global_Map_Location, Global_Map_Location_Def_Value)) {
            case 1:
            case 4:
            case 7:
                activityMapBinding.information.setText(Can_Not_Do_That_2);
                break;
            case 2: {
                sharedPreferences.edit().putInt(Global_Map_Location, 1).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_1()).commit();
                activityMapBinding.information.setText(Going_To_Southwest);
                activityMapBinding.currentLocation.setText(Current_Locaton_Southwest);
            }
            break;
            case 3: {
                sharedPreferences.edit().putInt(Global_Map_Location, 2).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).commit();
                activityMapBinding.information.setText(Going_To_South);
                activityMapBinding.currentLocation.setText(Current_Locaton_South);
            }
            break;
            case 5: {
                sharedPreferences.edit().putInt(Global_Map_Location, 4).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_4()).commit();
                activityMapBinding.information.setText(Going_To_West);
                activityMapBinding.currentLocation.setText(Current_Locaton_West);
            }
            break;
            case 6: {
                sharedPreferences.edit().putInt(Global_Map_Location, 5).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).commit();
                activityMapBinding.information.setText(Going_To_Center);
                activityMapBinding.currentLocation.setText(Current_Locaton_Center);
            }
            break;
            case 8: {
                sharedPreferences.edit().putInt(Global_Map_Location, 7).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_7()).commit();
                activityMapBinding.information.setText(Going_To_Northwest);
                activityMapBinding.currentLocation.setText(Current_Locaton_Northwest);
            }
            break;
            case 9: {
                sharedPreferences.edit().putInt(Global_Map_Location, 8).apply();
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).commit();
                activityMapBinding.information.setText(Going_To_North);
                activityMapBinding.currentLocation.setText(Current_Locaton_North);
            }
            break;
        }
    }

    private void creatingField() {
        if (!(getPreferences(MODE_PRIVATE).contains(Global_Map_Location))) {
            getPreferences(MODE_PRIVATE).edit().putInt(Global_Map_Location, 1).apply();
        }
    }

    private void startLocation(ActivityMapBinding activityMapBinding) {
        int container = R.id.linearLayout;
        sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Starting, true).apply();
        if (activityMapBinding.information.getText().equals("") || activityMapBinding.information.getText() == null) activityMapBinding.information.setText(Not_Going_Yet);
        switch (sharedPreferences.getInt(Global_Map_Location, 1)) {
            case 1: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_1()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_Southwest);
            }
            break;
            case 2: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_2()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_South);
            }
            break;
            case 3: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_3()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_Southeast);
            }
            break;
            case 4: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_4()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_West);
            }
            break;
            case 5: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_5()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_Center);
            }
            break;
            case 6: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_6()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_East);
            }
            break;
            case 7: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_7()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_Northwest);
            }
            break;
            case 8: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_8()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_North);
            }
            break;
            case 9: {
                getSupportFragmentManager().beginTransaction().replace(container, new GlobalMapFragment_9()).addToBackStack(null).commit();
                activityMapBinding.currentLocation.setText(Current_Locaton_Northeast);
            }
            break;
        }
    }
}