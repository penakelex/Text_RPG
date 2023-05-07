package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Characteristics;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Main_Information;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Skills;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;
import static penakelex.textRPG.homeland.Main.Constants.Level;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Age;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Height;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import androidx.appcompat.widget.AppCompatTextView;

import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.R;


public class StartingNewGameWithProgress extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.starting_new_game_with_progress, null);
        builder.setView(view);
        AppCompatTextView yesButton = view.findViewById(R.id.yesButton), noButton = view.findViewById(R.id.noButton);
        yesButton.setOnClickListener(listener -> startingNewGame());
        noButton.setOnClickListener(listener -> dismiss());
        return builder.create();
    }

    private void startingNewGame() {
        getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).edit().putInt(ID_Dialog, 0).
                putBoolean(Is_Game_Started, true).putBoolean(First_Visit_Talents, true).
                putBoolean(First_Visit_Skills, true).putBoolean(First_Visit_Main_Information, true).
                putBoolean(First_Visit_Characteristics, true).
                putInt(Main_Character_Age, 0).putInt(Main_Character_Height, 0).
                putString(Main_Character_Name, "").putInt(Gender, 0).
                putBoolean(First_Visit_Main_Information, false).
                putInt(Level, 0).apply();
        startActivity(new Intent(getActivity(), DialogActivity.class));
        getActivity().finish();
    }
}
