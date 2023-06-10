package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.HealthInformationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.OtherInformationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.ReputationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.StatisticsFragment;
import penakelex.textRPG.homeland.databinding.FragmentHealthAndOtherInformationBinding;

public class HealthAndOtherInformationFragment extends Fragment {
    private FragmentHealthAndOtherInformationBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHealthAndOtherInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new HealthInformationFragment()).commit();
        binding.navigationViewForOtherInformation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.healthInformation -> {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new HealthInformationFragment()).commit();
                    return true;
                }
                case R.id.reputation -> {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new ReputationFragment()).commit();
                    return true;
                }
                case R.id.statistics -> {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new StatisticsFragment()).commit();
                    return true;
                }
                case R.id.otherInformation -> {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new OtherInformationFragment()).commit();
                    return true;
                }
            }
            return false;
        });
    }
}