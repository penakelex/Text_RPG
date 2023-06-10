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

import penakelex.textRPG.homeland.Adapters.Statistics.StatisticsAdapter;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.ViewModels.StatisticsViewModel.StatisticsViewModel;
import penakelex.textRPG.homeland.databinding.StatisticsFragmentBinding;

public class StatisticsFragment extends Fragment {
    private StatisticsFragmentBinding binding;
    private StatisticsViewModel statisticsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false);
        statisticsViewModel = new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        statisticsViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        StatisticsAdapter adapter = new StatisticsAdapter();
        LiveData<List<StatisticItem>> statistics = statisticsViewModel.getAllStatistic();
        statistics.observe(requireActivity(), statisticItems -> {
            statistics.removeObservers(requireActivity());
            adapter.setInformation(requireContext(), statisticItems);
        });
        binding.containerForStatistics.setAdapter(adapter);
    }
}
