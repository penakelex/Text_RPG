package com.example.textrpg.GlobalMapFragments;

import static java.util.Arrays.asList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.textrpg.databinding.FragmentGlobalMap1Binding;
import com.example.textrpg.databinding.FragmentGlobalMap2Binding;


public class GlobalMapFragment_2 extends Fragment {
    private FragmentGlobalMap2Binding fragmentGlobalMap2Binding;
    private SharedPreferences sharedPreferences;
    private final int[] numbers = {4, 5, 6, 13, 14, 15, 22, 23, 24};
    private final String Local_Map_Location = "Local Map Location",
            Dot = ".", U_Here = "∇";
    private final int Local_Map_Location_Def_Value = 11;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentGlobalMap2Binding.second1.setOnClickListener(listener -> firstButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second2.setOnClickListener(listener -> secondButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second3.setOnClickListener(listener -> thirdButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second4.setOnClickListener(listener -> fourthButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second5.setOnClickListener(listener -> fifthButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second6.setOnClickListener(listener -> sixthButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second7.setOnClickListener(listener -> seventhButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second8.setOnClickListener(listener -> eighthButton(fragmentGlobalMap2Binding));
        fragmentGlobalMap2Binding.second9.setOnClickListener(listener -> ninthButton(fragmentGlobalMap2Binding));

    }

    private void ninthButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[8]).apply();
            fragmentGlobalMap2Binding.second9.setText(U_Here);
            fragmentGlobalMap2Binding.second9.setTextSize(42);
        }
    }

    private void eighthButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[7]).apply();
            fragmentGlobalMap2Binding.second8.setText(U_Here);
            fragmentGlobalMap2Binding.second8.setTextSize(42);
        }
    }

    private void seventhButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[6]).apply();
            fragmentGlobalMap2Binding.second7.setText(U_Here);
            fragmentGlobalMap2Binding.second7.setTextSize(42);
        }
    }

    private void sixthButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[5]).apply();
            fragmentGlobalMap2Binding.second6.setText(U_Here);
            fragmentGlobalMap2Binding.second6.setTextSize(42);
        }
    }

    private void fifthButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[4]).apply();
            fragmentGlobalMap2Binding.second5.setText(U_Here);
            fragmentGlobalMap2Binding.second5.setTextSize(42);
        }
    }

    private void fourthButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[3]).apply();
            fragmentGlobalMap2Binding.second4.setText(U_Here);
            fragmentGlobalMap2Binding.second4.setTextSize(42);
        }
    }

    private void thirdButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[2]).apply();
            fragmentGlobalMap2Binding.second3.setText(U_Here);
            fragmentGlobalMap2Binding.second3.setTextSize(42);
        }
    }

    private void secondButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[1]).apply();
            fragmentGlobalMap2Binding.second2.setText(U_Here);
            fragmentGlobalMap2Binding.second2.setTextSize(42);
        }
    }

    private void firstButton(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap2Binding);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[0]).apply();
            fragmentGlobalMap2Binding.second1.setText(U_Here);
            fragmentGlobalMap2Binding.second1.setTextSize(42);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentGlobalMap2Binding = FragmentGlobalMap2Binding.inflate(inflater, container, false);
        return fragmentGlobalMap2Binding.getRoot();
    }

    private void settingNothing(FragmentGlobalMap2Binding fragmentGlobalMap2Binding) {
        int location = getActivity().getPreferences(Context.MODE_PRIVATE).getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if (asList(numbers).contains(location)) {
            switch (location) {
                case 1:
                    fragmentGlobalMap2Binding.second1.setText(".");
                    break;
                case 2:
                    fragmentGlobalMap2Binding.second2.setText(".");
                    break;
                case 3:
                    fragmentGlobalMap2Binding.second3.setText(".");
                    break;
                case 4:
                    fragmentGlobalMap2Binding.second4.setText(".");
                    break;
                case 5:
                    fragmentGlobalMap2Binding.second5.setText(".");
                    break;
                case 6:
                    fragmentGlobalMap2Binding.second6.setText(".");
                    break;
                case 7:
                    fragmentGlobalMap2Binding.second7.setText(".");
                    break;
                case 8:
                    fragmentGlobalMap2Binding.second8.setText(".");
                    break;
                case 9:
                    fragmentGlobalMap2Binding.second9.setText(".");
                    break;
            }
        }
    }
}