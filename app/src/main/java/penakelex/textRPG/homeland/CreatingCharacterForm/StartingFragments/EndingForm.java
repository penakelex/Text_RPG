package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import penakelex.textRPG.homeland.Databases.HealthDatabase.HealthDatabaseHelper;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.R;


public class EndingForm extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.ending_form, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(view);
        AppCompatTextView yesButton = view.findViewById(R.id.endingFormYesButton), noButton = view.findViewById(R.id.endingFormNoButton);
        yesButton.setOnClickListener(listener -> endingForm());
        noButton.setOnClickListener(listener -> dismiss());
        return builder.create();
    }

    private void endingForm() {
        HealthDatabaseHelper.settingStartingValues(getActivity().getApplicationContext(), new String[]{getResources().getString(R.string.health_points), getResources().getString(R.string.left_arm), getResources().getString(R.string.right_arm), getResources().getString(R.string.head), getResources().getString(R.string.left_leg), getResources().getString(R.string.right_leg), getResources().getString(R.string.torso)});
        startActivity(new Intent(getActivity(), DialogActivity.class));
        getActivity().finish();
    }
}