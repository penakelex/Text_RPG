package com.example.textrpg.StartingFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;


public class AlertWindow extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Вы уверены, что хотите завершить заполнение анкеты?",
                yes = "Да, я уверен.", nah = "Нет, я не уверен.";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setPositiveButton(yes, (dialog, which) -> StartingTalentsFragment.endForm = true);
        builder.setNegativeButton(nah, ((dialog, which) -> StartingTalentsFragment.endForm = false));
        builder.setCancelable(true);
        return builder.create();
    }
}
