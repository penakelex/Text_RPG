package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Constants.First_Visit_Characteristics;
import static penakelex.textRPG.homeland.Constants.First_Visit_Main_Information;
import static penakelex.textRPG.homeland.Constants.First_Visit_Skills;
import static penakelex.textRPG.homeland.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Constants.Is_Game_Started;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.StartingFragments.StartingTalentsFragment;

public class StartingNewGameWithProgress extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Создание новой игры", message = "Если Вы начнёте новую игру, Ваш текущий прогресс сбросится.",
                yes = "Да, я хочу это", nah = "Нет, я передумал";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(yes, ((dialog, which) -> startingNewGame()));
        builder.setNegativeButton(nah, ((dialog, which) -> StartingTalentsFragment.endForm = false));
        return builder.create();
    }

    private void startingNewGame() {
        getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).edit().putInt(ID_Dialog, 0).
                putBoolean(Is_Game_Started, true).putBoolean(First_Visit_Talents, true).
                putBoolean(First_Visit_Skills, true).putBoolean(First_Visit_Main_Information, true).
                putBoolean(First_Visit_Characteristics, true).apply();
        startActivity(new Intent(getActivity(), DialogActivity.class));
        getActivity().finish();
    }
}
