package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.Statistics.StatisticsAdapter;
import penakelex.textRPG.homeland.databinding.StatisticsFragmentBinding;

public class StatisticsFragment extends Fragment {
    private StatisticsFragmentBinding binding;
    private StatisticsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StatisticsAdapter();
        adapter.setInformation(getActivity().getApplicationContext());
        binding.containerForStatistics.setAdapter(adapter);
    }
}
