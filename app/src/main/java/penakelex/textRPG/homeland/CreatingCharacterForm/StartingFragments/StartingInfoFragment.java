package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.StartingOtherInformation.StartingOtherInformationAdapter;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.databinding.FragmentStartingInfoBinding;

public class StartingInfoFragment extends Fragment {
    private FragmentStartingInfoBinding binding;
    private StartingOtherInformationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StartingOtherInformationAdapter();
        adapter.setInformation(getActivity());
        binding.containerForStartingOtherInformation.setAdapter(adapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Is_Going_To_Starting_Information_First_Time, true)) {
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    binding = null;
                    startActivity(new Intent(getActivity(), DialogActivity.class));
                    getActivity().finish();
                }
            }.start();
        }
        binding.signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!sharedPreferences.getBoolean(Is_Going_To_Starting_Information_First_Time, true)) {
                    if (sharedPreferences.getString(Main_Character_Name, "").equals(s.toString())) {
                        binding = null;
                        startActivity(new Intent(getActivity(), DialogActivity.class));
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}