package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.HealthStatuses.HealthStatusAdapter;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.HealthViewModel.HealthViewModel;
import penakelex.textRPG.homeland.databinding.FragmentHealthInfromationBinding;

public class HealthInformationFragment extends Fragment {
    private FragmentHealthInfromationBinding binding;
    private HealthViewModel healthViewModel;
    private final HealthStatusAdapter.OnHealthItemClickListener clickListener = new HealthStatusAdapter.OnHealthItemClickListener() {
        @Override
        public void onClickListener(HealthItem healthItem) {
            binding.healthStatusName.setText(requireActivity().getResources().getString(healthItem.getName()));
            switch (healthItem.getID()) {
                case 1:
                    binding.healthStatusDescription.setText(getResources().getString(R.string.health_points_description));
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    switch (healthItem.getValue()) {
                        case 0 ->
                                binding.healthStatusDescription.setText(getResources().getString(R.string.damaged_));
                        case 1 ->
                                binding.healthStatusDescription.setText(getResources().getString(R.string.all_good));
                    }
                    break;
                case 7:
                    switch (healthItem.getValue()) {
                        case 0 ->
                                binding.healthStatusDescription.setText(getResources().getString(R.string.damaged));
                        case 1 ->
                                binding.healthStatusDescription.setText(getResources().getString(R.string.all_good));
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHealthInfromationBinding.inflate(inflater, container, false);
        healthViewModel = new ViewModelProvider(requireActivity()).get(HealthViewModel.class);
        healthViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        HealthStatusAdapter adapter = new HealthStatusAdapter(clickListener);
        LiveData<List<HealthItem>> health = healthViewModel.getAllHealthStatuses();
        health.observe(requireActivity(), healthItems -> {
            health.removeObservers(requireActivity());
            adapter.setInformation(requireActivity(), healthItems);
        });
        binding.containerForHealthStatuses.setAdapter(adapter);
    }
}
