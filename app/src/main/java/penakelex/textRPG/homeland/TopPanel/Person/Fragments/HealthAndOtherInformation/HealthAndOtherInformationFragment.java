package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.HealthInformation;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.OtherInformationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.ReputationFragment;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments.StatisticsFragment;
import penakelex.textRPG.homeland.databinding.FragmentHealthAndOtherInformationBinding;

public class HealthAndOtherInformationFragment extends Fragment {
    private FragmentHealthAndOtherInformationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthAndOtherInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new HealthInformation()).commit();
        binding.navigationViewForOtherInformation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.healthInformation:
                    getFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new HealthInformation()).commit();
                    return true;
                case R.id.reputation:
                    getFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new ReputationFragment()).commit();
                    return true;
                case R.id.statistics:
                    getFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new StatisticsFragment()).commit();
                    return true;
                case R.id.otherInformation:
                    getFragmentManager().beginTransaction().replace(binding.containerForOtherInformation.getId(), new OtherInformationFragment()).commit();
                    return true;
            }
            return false;
        });
    }
}