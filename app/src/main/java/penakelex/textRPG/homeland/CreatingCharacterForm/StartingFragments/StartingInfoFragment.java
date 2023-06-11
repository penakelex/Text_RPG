package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

import penakelex.textRPG.homeland.Adapters.StartingOtherInformation.StartingOtherInformationAdapter;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.databinding.FragmentStartingInfoBinding;

public class StartingInfoFragment extends Fragment {
    private FragmentStartingInfoBinding binding;
    private OtherInformationViewModel otherInformationViewModel;
    private SharedPreferences sharedPreferences;

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
        List<OtherInformationItem> otherInformation = otherInformationViewModel.getAllOtherInformation();
        adapter.setInformation(otherInformation, requireActivity());
        binding.containerForStartingOtherInformation.setAdapter(adapter);
        sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        Objects.requireNonNull(binding.ask).setOnClickListener(listener -> asking());
        binding.signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sharedPreferences.getString(Main_Character_Name, "").contentEquals(s)) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sharedPreferences.edit().putInt(ID_Dialog, 3).apply();
                                sleep(600);
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

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void asking() {
        sharedPreferences.edit().putInt(ID_Dialog, 2).apply();
        startActivity(new Intent(requireActivity(), DialogActivity.class));
        requireActivity().finish();
    }
}