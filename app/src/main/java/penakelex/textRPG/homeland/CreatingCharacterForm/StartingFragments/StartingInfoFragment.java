package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Is_Going_To_Starting_Information_First_Time;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.StartingOtherInformation.StartingOtherInformationAdapter;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingInfoBinding;

public class StartingInfoFragment extends Fragment {
    private FragmentStartingInfoBinding binding;
    private OtherInformationViewModel otherInformationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartingInfoBinding.inflate(inflater, container, false);
        otherInformationViewModel = new ViewModelProvider(requireActivity()).get(OtherInformationViewModel.class);
        otherInformationViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StartingOtherInformationAdapter adapter = new StartingOtherInformationAdapter();
        LiveData<List<OtherInformationItem>> otherInformation = otherInformationViewModel.getAllOtherInformation();
        otherInformation.observe(requireActivity(), otherInformationItems -> {
            otherInformation.removeObservers(requireActivity());
            adapter.setInformation(otherInformationItems, requireActivity());
        });
        binding.containerForStartingOtherInformation.setAdapter(adapter);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Is_Going_To_Starting_Information_First_Time, true)) {
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    binding = null;
                    startActivity(new Intent(getActivity(), DialogActivity.class));
                    requireActivity().finish();
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
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(400);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    binding = null;
                                    startActivity(new Intent(getActivity(), DialogActivity.class));
                                    requireActivity().finish();
                                }
                            }
                        };
                        thread.start();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}