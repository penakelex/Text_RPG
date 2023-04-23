package com.example.textrpg.GlobalMapFragments;

import static com.example.textrpg.Constants.Global_Map_3;
import static com.example.textrpg.Constants.Local_Map_Location;
import static com.example.textrpg.Constants.Local_Map_Location_Def_Value;
import static com.example.textrpg.Constants.Starting;
import static com.example.textrpg.Constants.U_Here;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.textrpg.databinding.FragmentGlobalMap3Binding;


public class GlobalMapFragment_3 extends Fragment {
    private FragmentGlobalMap3Binding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button1.setOnClickListener(listener -> firstButton());
        binding.button2.setOnClickListener(listener -> secondButton());
        binding.button3.setOnClickListener(listener -> thirdButton());
        binding.button4.setOnClickListener(listener -> fourthButton());
        binding.button5.setOnClickListener(listener -> fifthButton());
        binding.button6.setOnClickListener(listener -> sixthButton());
        binding.button7.setOnClickListener(listener -> seventhButton());
        binding.button8.setOnClickListener(listener -> eighthButton());
        binding.button9.setOnClickListener(listener -> ninthButton());
    }

    private void startLocation() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value)) {
            case 7:
                binding.button1.setText(U_Here);
                break;
            case 8:
                binding.button2.setText(U_Here);
                break;
            case 9:
                binding.button3.setText(U_Here);
                break;
            case 16:
                binding.button4.setText(U_Here);
                break;
            case 17:
                binding.button5.setText(U_Here);
                break;
            case 18:
                binding.button6.setText(U_Here);
                break;
            case 25:
                binding.button7.setText(U_Here);
                break;
            case 26:
                binding.button8.setText(U_Here);
                break;
            case 27:
                binding.button9.setText(U_Here);
                break;
        }
    }


    private void ninthButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[8]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[8]).apply();
            binding.button9.setText(U_Here);
        }
    }

    private void eighthButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[7]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[7]).apply();
            binding.button8.setText(U_Here);
        }
    }

    private void seventhButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[6]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[6]).apply();
            binding.button7.setText(U_Here);
        }
    }

    private void sixthButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[5]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[5]).apply();
            binding.button6.setText(U_Here);
        }
    }

    private void fifthButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[4]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[4]).apply();
            binding.button5.setText(U_Here);
        }
    }

    private void fourthButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[3]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[3]).apply();
            binding.button4.setText(U_Here);
        }
    }

    private void thirdButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[2]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[2]).apply();
            binding.button3.setText(U_Here);
        }
    }

    private void secondButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[1]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[1]).apply();
            binding.button2.setText(U_Here);
        }
    }

    private void firstButton() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(Global_Map_3[0]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_3[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_3[0]).apply();
            binding.button1.setText(U_Here);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGlobalMap3Binding.inflate(inflater, container, false);
        startLocation();
        return binding.getRoot();
    }

    private void settingNothing(int destination) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if ((location == Global_Map_3[0] || location == Global_Map_3[1] || location == Global_Map_3[2] ||
                location == Global_Map_3[3] || location == Global_Map_3[4] || location == Global_Map_3[5] ||
                location == Global_Map_3[6] || location == Global_Map_3[7] || location == Global_Map_3[8]) && destination != location && !sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
        } else if (sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
            sharedPreferences.edit().putBoolean(Starting, false).apply();
        }
    }

    private void settingNothingToLocation(int location) {
        switch (location) {
            case 7:
                binding.button1.setText("");
                break;
            case 8:
                binding.button2.setText("");
                break;
            case 9:
                binding.button3.setText("");
                break;
            case 16:
                binding.button4.setText("");
                break;
            case 17:
                binding.button5.setText("");
                break;
            case 18:
                binding.button6.setText("");
                break;
            case 25:
                binding.button7.setText("");
                break;
            case 26:
                binding.button8.setText("");
                break;
            case 27:
                binding.button9.setText("");
                break;
        }
    }
}