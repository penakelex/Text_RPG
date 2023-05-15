package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.HealthStatuses.HealthStatusAdapter;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentHealthInfromationBinding;

public class HealthInformation extends Fragment {
    private FragmentHealthInfromationBinding binding;
    private HealthStatusAdapter adapter;
    private HealthStatusAdapter.OnHealthItemClickListener clickListener = new HealthStatusAdapter.OnHealthItemClickListener() {
        @Override
        public void onClickListener(String name, int position, short value) {
            binding.healthStatusName.setText(name);
            switch (position) {
                case 0:
                    binding.healthStatusDescription.setText(getResources().getString(R.string.health_points_description));
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    switch (value) {
                        case 0:
                            binding.healthStatusDescription.setText(getResources().getString(R.string.damaged_));
                            break;
                        case 1:
                            binding.healthStatusDescription.setText(getResources().getString(R.string.all_good));
                            break;
                    }
                    break;
                case 6:
                    switch (value) {
                        case 0:
                            binding.healthStatusDescription.setText(getResources().getString(R.string.damaged));
                            break;
                        case 1:
                            binding.healthStatusDescription.setText(getResources().getString(R.string.all_good));
                            break;
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHealthInfromationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HealthStatusAdapter(clickListener);
        adapter.setInformation(getActivity().getApplicationContext());
        binding.containerForHealthStatuses.setAdapter(adapter);
    }
}
