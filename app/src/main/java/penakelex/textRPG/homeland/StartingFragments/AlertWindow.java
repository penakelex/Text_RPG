package penakelex.textRPG.homeland.StartingFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import penakelex.textRPG.homeland.Dialogs.DialogActivity;


public class AlertWindow extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = "Вы уверены, что хотите завершить заполнение анкеты?",
                yes = "Да, я уверен.", nah = "Нет, я не уверен.";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setPositiveButton(yes, (dialog, which) -> {
            startActivity(new Intent(getActivity(), DialogActivity.class));
            getActivity().finish();
        });
        builder.setNegativeButton(nah, (dialog, which) -> StartingTalentsFragment.endForm = false);
        builder.setCancelable(true);
        return builder.create();
    }
}
