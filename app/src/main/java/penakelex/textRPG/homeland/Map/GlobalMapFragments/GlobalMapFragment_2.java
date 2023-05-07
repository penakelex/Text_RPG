package penakelex.textRPG.homeland.Map.GlobalMapFragments;

import static penakelex.textRPG.homeland.Main.Constants.Global_Map_2;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location_Def_Value;
import static penakelex.textRPG.homeland.Main.Constants.Starting;
import static penakelex.textRPG.homeland.Main.Constants.U_Here;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import penakelex.textRPG.homeland.databinding.FragmentGlobalMap2Binding;


public class GlobalMapFragment_2 extends Fragment {
    private FragmentGlobalMap2Binding binding;
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
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value)) {
            case 4:
                binding.button1.setText(U_Here);
                break;
            case 5:
                binding.button2.setText(U_Here);
                break;
            case 6:
                binding.button3.setText(U_Here);
                break;
            case 13:
                binding.button4.setText(U_Here);
                break;
            case 14:
                binding.button5.setText(U_Here);
                break;
            case 15:
                binding.button6.setText(U_Here);
                break;
            case 22:
                binding.button7.setText(U_Here);
                break;
            case 23:
                binding.button8.setText(U_Here);
                break;
            case 24:
                binding.button9.setText(U_Here);
                break;
        }
    }


    private void ninthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[8]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[8]).apply();
            binding.button9.setText(U_Here);
        }
    }

    private void eighthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[7]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[7]).apply();
            binding.button8.setText(U_Here);
        }
    }

    private void seventhButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[6]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[6]).apply();
            binding.button7.setText(U_Here);
        }
    }

    private void sixthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[5]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[5]).apply();
            binding.button6.setText(U_Here);
        }
    }

    private void fifthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[4]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[4]).apply();
            binding.button5.setText(U_Here);
        }
    }

    private void fourthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[3]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[3]).apply();
            binding.button4.setText(U_Here);
        }
    }

    private void thirdButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[2]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[2]).apply();
            binding.button3.setText(U_Here);
        }
    }

    private void secondButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[1]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[1]).apply();
            binding.button2.setText(U_Here);
        }
    }

    private void firstButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_2[0]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_2[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_2[0]).apply();
            binding.button1.setText(U_Here);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGlobalMap2Binding.inflate(inflater, container, false);
        startLocation();
        return binding.getRoot();
    }

    private void settingNothing(int destination) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if ((location == Global_Map_2[0] || location == Global_Map_2[1] || location == Global_Map_2[2] ||
                location == Global_Map_2[3] || location == Global_Map_2[4] || location == Global_Map_2[5] ||
                location == Global_Map_2[6] || location == Global_Map_2[7] || location == Global_Map_2[8]) && destination != location && !sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
        } else if (sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
            sharedPreferences.edit().putBoolean(Starting, false).apply();
        }
    }

    private void settingNothingToLocation(int location) {
        switch (location) {
            case 4:
                binding.button1.setText("");
                break;
            case 5:
                binding.button2.setText("");
                break;
            case 6:
                binding.button3.setText("");
                break;
            case 13:
                binding.button4.setText("");
                break;
            case 14:
                binding.button5.setText("");
                break;
            case 15:
                binding.button6.setText("");
                break;
            case 22:
                binding.button7.setText("");
                break;
            case 23:
                binding.button8.setText("");
                break;
            case 24:
                binding.button9.setText("");
                break;
        }
    }
}