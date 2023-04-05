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
import android.widget.Toast;

import com.example.textrpg.databinding.FragmentGlobalMap1Binding;


public class GlobalMapFragment_1 extends Fragment {
    private FragmentGlobalMap1Binding fragmentGlobalMap1Binding;
    private SharedPreferences sharedPreferences;
    private final int[] numbers = {1, 2, 3, 10, 11, 12, 19, 20, 21};
    private final String Local_Map_Location = "Local Map Location", Starting = "Starting",
            Dot = ".", U_Here = "∇";
    private final int Local_Map_Location_Def_Value = 11;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentGlobalMap1Binding.first1.setOnClickListener(listener -> firstButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first2.setOnClickListener(listener -> secondButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first3.setOnClickListener(listener -> thirdButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first4.setOnClickListener(listener -> fourthButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first5.setOnClickListener(listener -> fifthButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first6.setOnClickListener(listener -> sixthButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first7.setOnClickListener(listener -> seventhButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first8.setOnClickListener(listener -> eighthButton(fragmentGlobalMap1Binding));
        fragmentGlobalMap1Binding.first9.setOnClickListener(listener -> ninthButton(fragmentGlobalMap1Binding));
    }

    private void startLocation() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);

        switch (location) {
            case 1:
                fragmentGlobalMap1Binding.first1.setText(U_Here);
                fragmentGlobalMap1Binding.first1.setTextSize(42);
                break;
            case 2:
                fragmentGlobalMap1Binding.first2.setText(U_Here);
                fragmentGlobalMap1Binding.first2.setTextSize(42);
                break;
            case 3:
                fragmentGlobalMap1Binding.first3.setText(U_Here);
                fragmentGlobalMap1Binding.first3.setTextSize(42);
                break;
            case 10:
                fragmentGlobalMap1Binding.first4.setText(U_Here);
                fragmentGlobalMap1Binding.first4.setTextSize(42);
                break;
            case 11:
                fragmentGlobalMap1Binding.first5.setText(U_Here);
                fragmentGlobalMap1Binding.first5.setTextSize(42);
                break;
            case 12:
                fragmentGlobalMap1Binding.first6.setText(U_Here);
                fragmentGlobalMap1Binding.first6.setTextSize(42);
                break;
            case 19:
                fragmentGlobalMap1Binding.first7.setText(U_Here);
                fragmentGlobalMap1Binding.first7.setTextSize(42);
                break;
            case 20:
                fragmentGlobalMap1Binding.first8.setText(U_Here);
                fragmentGlobalMap1Binding.first8.setTextSize(42);
                break;
            case 21:
                fragmentGlobalMap1Binding.first9.setText(U_Here);
                fragmentGlobalMap1Binding.first9.setTextSize(42);
                break;
        }
    }


    private void ninthButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[8]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[8]).apply();
            fragmentGlobalMap1Binding.first9.setText(U_Here);
            fragmentGlobalMap1Binding.first9.setTextSize(42);
        }
    }

    private void eighthButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[7]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[7]).apply();
            fragmentGlobalMap1Binding.first8.setText(U_Here);
            fragmentGlobalMap1Binding.first8.setTextSize(42);
        }
    }

    private void seventhButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[6]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[6]).apply();
            fragmentGlobalMap1Binding.first7.setText(U_Here);
            fragmentGlobalMap1Binding.first7.setTextSize(42);
        }
    }

    private void sixthButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[5]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[5]).apply();
            fragmentGlobalMap1Binding.first6.setText(U_Here);
            fragmentGlobalMap1Binding.first6.setTextSize(42);
        }
    }

    private void fifthButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[4]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[4]).apply();
            fragmentGlobalMap1Binding.first5.setText(U_Here);
            fragmentGlobalMap1Binding.first5.setTextSize(42);
        }
    }

    private void fourthButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[3]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[3]).apply();
            fragmentGlobalMap1Binding.first4.setText(U_Here);
            fragmentGlobalMap1Binding.first4.setTextSize(42);
        }
    }

    private void thirdButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[2]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[2]).apply();
            fragmentGlobalMap1Binding.first3.setText(U_Here);
            fragmentGlobalMap1Binding.first3.setTextSize(42);
        }
    }

    private void secondButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[1]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[1]).apply();
            fragmentGlobalMap1Binding.first2.setText(U_Here);
            fragmentGlobalMap1Binding.first2.setTextSize(42);
        }
    }

    private void firstButton(FragmentGlobalMap1Binding fragmentGlobalMap1Binding) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingNothing(fragmentGlobalMap1Binding, numbers[0]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != numbers[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, numbers[0]).apply();
            fragmentGlobalMap1Binding.first1.setText(U_Here);
            //Toast.makeText(getActivity(), String.valueOf(fragmentGlobalMap1Binding.first1.getTextSize()), Toast.LENGTH_LONG).show();
            fragmentGlobalMap1Binding.first1.setTextSize(42); //110.25/63
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentGlobalMap1Binding = FragmentGlobalMap1Binding.inflate(inflater, container, false);
        startLocation();
        return fragmentGlobalMap1Binding.getRoot();
    }

    private void settingNothing(FragmentGlobalMap1Binding fragmentGlobalMap1Binding, int destination) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if ((location == numbers[0] || location == numbers[1] || location == numbers[2] ||
                location == numbers[3] || location == numbers[4] || location == numbers[5] ||
                location == numbers[6] || location == numbers[7] || location == numbers[8]) && destination != location && !sharedPreferences.getBoolean(Starting, true)) {
            switch (location) {
                case 1:
                    fragmentGlobalMap1Binding.first1.setText(Dot);
                    fragmentGlobalMap1Binding.first1.setTextSize(10);
                    break;
                case 2:
                    fragmentGlobalMap1Binding.first2.setText(Dot);
                    fragmentGlobalMap1Binding.first2.setTextSize(10);
                    break;
                case 3:
                    fragmentGlobalMap1Binding.first3.setText(Dot);
                    fragmentGlobalMap1Binding.first3.setTextSize(10);
                    break;
                case 10:
                    fragmentGlobalMap1Binding.first4.setText(Dot);
                    fragmentGlobalMap1Binding.first4.setTextSize(10);
                    break;
                case 11:
                    fragmentGlobalMap1Binding.first5.setText(Dot);
                    fragmentGlobalMap1Binding.first5.setTextSize(10);
                    break;
                case 12:
                    fragmentGlobalMap1Binding.first6.setText(Dot);
                    fragmentGlobalMap1Binding.first6.setTextSize(10);
                    break;
                case 19:
                    fragmentGlobalMap1Binding.first7.setText(Dot);
                    fragmentGlobalMap1Binding.first7.setTextSize(10);
                    break;
                case 20:
                    fragmentGlobalMap1Binding.first8.setText(Dot);
                    fragmentGlobalMap1Binding.first8.setTextSize(10);
                    break;
                case 21:
                    fragmentGlobalMap1Binding.first9.setText(Dot);
                    fragmentGlobalMap1Binding.first9.setTextSize(10);
                    break;
            }
        } else if (sharedPreferences.getBoolean(Starting, true)) {
            switch (location) {
                case 1:
                    fragmentGlobalMap1Binding.first1.setText(Dot);
                    fragmentGlobalMap1Binding.first1.setTextSize(10);
                    break;
                case 2:
                    fragmentGlobalMap1Binding.first2.setText(Dot);
                    fragmentGlobalMap1Binding.first2.setTextSize(10);
                    break;
                case 3:
                    fragmentGlobalMap1Binding.first3.setText(Dot);
                    fragmentGlobalMap1Binding.first3.setTextSize(10);
                    break;
                case 10:
                    fragmentGlobalMap1Binding.first4.setText(Dot);
                    fragmentGlobalMap1Binding.first4.setTextSize(10);
                    break;
                case 11:
                    fragmentGlobalMap1Binding.first5.setText(Dot);
                    fragmentGlobalMap1Binding.first5.setTextSize(10);
                    break;
                case 12:
                    fragmentGlobalMap1Binding.first6.setText(Dot);
                    fragmentGlobalMap1Binding.first6.setTextSize(10);
                    break;
                case 19:
                    fragmentGlobalMap1Binding.first7.setText(Dot);
                    fragmentGlobalMap1Binding.first7.setTextSize(10);
                    break;
                case 20:
                    fragmentGlobalMap1Binding.first8.setText(Dot);
                    fragmentGlobalMap1Binding.first8.setTextSize(10);
                    break;
                case 21:
                    fragmentGlobalMap1Binding.first9.setText(Dot);
                    fragmentGlobalMap1Binding.first9.setTextSize(10);
                    break;
            }
        }
        sharedPreferences.edit().putBoolean(Starting, false).apply();
    }
}