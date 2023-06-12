package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;
import static penakelex.textRPG.homeland.Main.Constants.Level;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Age;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Height;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelStoreOwner;

import penakelex.textRPG.homeland.Databases.Database.DatabaseCallback;
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
        settingNothingInValues();
        startActivity(new Intent(getActivity(), DialogActivity.class));
        getActivity().finish();
    }

    private void settingNothingInValues() {
        getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).edit().putInt(ID_Dialog, 0).
                putBoolean(Is_Game_Started, true).putBoolean(First_Visit_Talents, true).
                putInt(Main_Character_Age, 0).putInt(Main_Character_Height, 0).
                putString(Main_Character_Name, "").putInt(Gender, 0).
                putInt(Characteristics_Points, 2).putInt(Main_Skills, 3).putInt(Talents_Points, 2).
                putInt(Level, 0).apply();
        new DatabaseCallback(getActivity().getApplication(), (ViewModelStoreOwner) getActivity());
    }
}
