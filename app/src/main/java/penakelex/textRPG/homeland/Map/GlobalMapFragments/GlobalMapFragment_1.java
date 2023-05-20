package penakelex.textRPG.homeland.Map.GlobalMapFragments;

import static penakelex.textRPG.homeland.Main.Constants.Global_Map_1;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location_Def_Value;
import static penakelex.textRPG.homeland.Main.Constants.Starting;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;
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

import java.util.Objects;

import penakelex.textRPG.homeland.databinding.FragmentGlobalMap1Binding;


public class GlobalMapFragment_1 extends Fragment implements GlobalMap {
    private FragmentGlobalMap1Binding binding;
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

    @Override
    public void setClickable() {
        sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Static_Position, false)) {
            binding.button1.setEnabled(false);
            binding.button2.setEnabled(false);
            binding.button3.setEnabled(false);
            binding.button4.setEnabled(false);
            binding.button5.setEnabled(false);
            binding.button6.setEnabled(false);
            binding.button7.setEnabled(false);
            binding.button8.setEnabled(false);
            binding.button9.setEnabled(false);
        } else {
            binding.button1.setEnabled(true);
            binding.button2.setEnabled(true);
            binding.button3.setEnabled(true);
            binding.button4.setEnabled(true);
            binding.button5.setEnabled(true);
            binding.button6.setEnabled(true);
            binding.button7.setEnabled(true);
            binding.button8.setEnabled(true);
            binding.button9.setEnabled(true);
        }
    }

    @Override
    public void startLocation() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value)) {
            case 1:
                binding.button1.setText(U_Here);
                break;
            case 2:
                binding.button2.setText(U_Here);
                break;
            case 3:
                binding.button3.setText(U_Here);
                break;
            case 10:
                binding.button4.setText(U_Here);
                break;
            case 11:
                binding.button5.setText(U_Here);
                break;
            case 12:
                binding.button6.setText(U_Here);
                break;
            case 19:
                binding.button7.setText(U_Here);
                break;
            case 20:
                binding.button8.setText(U_Here);
                break;
            case 21:
                binding.button9.setText(U_Here);
                break;
        }
    }

    @Override
    public void ninthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[8]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[8]).apply();
            binding.button9.setText(U_Here);
        }
    }

    @Override
    public void eighthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[7]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[7]).apply();
            binding.button8.setText(U_Here);
        }
    }

    @Override
    public void seventhButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[6]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[6]).apply();
            binding.button7.setText(U_Here);
        }
    }

    @Override

    public void sixthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[5]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[5]).apply();
            binding.button6.setText(U_Here);
        }
    }

    @Override
    public void fifthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[4]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[4]).apply();
            binding.button5.setText(U_Here);
        }
    }

    @Override
    public void fourthButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[3]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[3]).apply();
            binding.button4.setText(U_Here);
        }
    }

    @Override
    public void thirdButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[2]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[2]).apply();
            binding.button3.setText(U_Here);
        }
    }

    @Override
    public void secondButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[1]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[1]).apply();
            binding.button2.setText(U_Here);
        }
    }

    @Override
    public void firstButton() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingNothing(Global_Map_1[0]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != Global_Map_1[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, Global_Map_1[0]).apply();
            binding.button1.setText(U_Here);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGlobalMap1Binding.inflate(inflater, container, false);
        setClickable();
        startLocation();
        return binding.getRoot();
    }

    @Override
    public void settingNothing(int destination) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if ((location == Global_Map_1[0] || location == Global_Map_1[1] || location == Global_Map_1[2] ||
                location == Global_Map_1[3] || location == Global_Map_1[4] || location == Global_Map_1[5] ||
                location == Global_Map_1[6] || location == Global_Map_1[7] || location == Global_Map_1[8]) && destination != location && !sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
        } else if (sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocation(location);
            sharedPreferences.edit().putBoolean(Starting, false).apply();
        }
    }

    @Override
    public void settingNothingToLocation(int location) {
        switch (location) {
            case 1:
                binding.button1.setText("");
                break;
            case 2:
                binding.button2.setText("");
                break;
            case 3:
                binding.button3.setText("");
                break;
            case 10:
                binding.button4.setText("");
                break;
            case 11:
                binding.button5.setText("");
                break;
            case 12:
                binding.button6.setText("");
                break;
            case 19:
                binding.button7.setText("");
                break;
            case 20:
                binding.button8.setText("");
                break;
            case 21:
                binding.button9.setText("");
                break;
        }
    }
}