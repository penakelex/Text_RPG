package penakelex.textRPG.homeland.Map.MapFragment;

import static penakelex.textRPG.homeland.Main.Constants.Global_Map_1;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_2;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_3;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_4;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_5;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_6;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_7;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_8;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_9;
import static penakelex.textRPG.homeland.Main.Constants.Global_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location;
import static penakelex.textRPG.homeland.Main.Constants.Local_Map_Location_Def_Value;
import static penakelex.textRPG.homeland.Main.Constants.Starting;
import static penakelex.textRPG.homeland.Main.Constants.Static_Position;
import static penakelex.textRPG.homeland.Main.Constants.U_Here;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.MapFragmentBinding;

public class MapHelper {
    private int[] currentLocation;
    private final SharedPreferences sharedPreferences;
    private final MapFragmentBinding binding;
    private final Context context;

    public MapHelper(Context context, MapFragmentBinding binding) {
        this.sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        this.binding = binding;
        this.context = context;
    }

    private void setCurrentLocation(byte currentLocation) {
        switch (currentLocation) {
            case 1 -> this.currentLocation = Global_Map_1;
            case 2 -> this.currentLocation = Global_Map_2;
            case 3 -> this.currentLocation = Global_Map_3;
            case 4 -> this.currentLocation = Global_Map_4;
            case 5 -> this.currentLocation = Global_Map_5;
            case 6 -> this.currentLocation = Global_Map_6;
            case 7 -> this.currentLocation = Global_Map_7;
            case 8 -> this.currentLocation = Global_Map_8;
            case 9 -> this.currentLocation = Global_Map_9;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void changeImage(byte location) {
        sharedPreferences.edit().putInt(Global_Map_Location, location).apply();
        switch (location) {
            case 1 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_1));
            case 2 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_2));
            case 3 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_3));
            case 4 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_4));
            case 5 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_5));
            case 6 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_6));
            case 7 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_7));
            case 8 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_8));
            case 9 -> binding.background.setBackground(context.getDrawable(R.drawable.map_part_9));
        }
        setCurrentLocation(location);
    }

    public void setClickable() {
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

    public void startLocalLocation() {
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if (location == currentLocation[0]) {
            binding.button1.setText(U_Here);
        } else if (location == currentLocation[1]) {
            binding.button2.setText(U_Here);
        } else if (location == currentLocation[2]) {
            binding.button3.setText(U_Here);
        } else if (location == currentLocation[3]) {
            binding.button4.setText(U_Here);
        } else if (location == currentLocation[4]) {
            binding.button5.setText(U_Here);
        } else if (location == currentLocation[5]) {
            binding.button6.setText(U_Here);
        } else if (location == currentLocation[6]) {
            binding.button7.setText(U_Here);
        } else if (location == currentLocation[7]) {
            binding.button8.setText(U_Here);
        } else if (location == currentLocation[8]) {
            binding.button9.setText(U_Here);
        }
    }

    public void firstButton() {
        settingNothing(currentLocation[0]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[0]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[0]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void secondButton() {
        settingNothing(currentLocation[1]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[1]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[1]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void thirdButton() {
        settingNothing(currentLocation[2]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[2]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[2]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void fourthButton() {
        settingNothing(currentLocation[3]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[3]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[3]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void fifthButton() {
        settingNothing(currentLocation[4]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[4]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[4]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void sixthButton() {
        settingNothing(currentLocation[5]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[5]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[5]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void seventhButton() {
        settingNothing(currentLocation[6]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[6]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[6]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void eighthButton() {
        settingNothing(currentLocation[7]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[7]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[7]).apply();
            binding.button1.setText(U_Here);
        }
    }

    public void ninthButton() {
        settingNothing(currentLocation[8]);
        if (sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value) != currentLocation[8]) {
            sharedPreferences.edit().putInt(Local_Map_Location, currentLocation[8]).apply();
            binding.button1.setText(U_Here);
        }
    }

    private void settingNothing(int destination) {
        int location = sharedPreferences.getInt(Local_Map_Location, Local_Map_Location_Def_Value);
        if (newLocation(location, destination)) {
            settingNothingToLocalLocation(location);
        } else if (sharedPreferences.getBoolean(Starting, true)) {
            settingNothingToLocalLocation(location);
            sharedPreferences.edit().putBoolean(Starting, false).apply();
        }
    }

    private boolean newLocation(int location, int destination) {
        return (location == currentLocation[0] || location == currentLocation[1] || location == currentLocation[2] ||
                location == currentLocation[3] || location == currentLocation[4] || location == currentLocation[5] ||
                location == currentLocation[6] || location == currentLocation[7] || location == currentLocation[8]) &&
                destination != location && !sharedPreferences.getBoolean(Starting, true);
    }

    private void settingNothingToLocalLocation(int location) {
        if (location == currentLocation[0]) {
            binding.button1.setText("");
        } else if (location == currentLocation[1]) {
            binding.button2.setText("");
        } else if (location == currentLocation[2]) {
            binding.button3.setText("");
        } else if (location == currentLocation[3]) {
            binding.button4.setText("");
        } else if (location == currentLocation[4]) {
            binding.button5.setText("");
        } else if (location == currentLocation[5]) {
            binding.button6.setText("");
        } else if (location == currentLocation[6]) {
            binding.button7.setText("");
        } else if (location == currentLocation[7]) {
            binding.button8.setText("");
        } else if (location == currentLocation[8]) {
            binding.button9.setText("");
        }
    }
}
