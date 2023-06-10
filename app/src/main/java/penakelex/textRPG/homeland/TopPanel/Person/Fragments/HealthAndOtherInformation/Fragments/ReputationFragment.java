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

import penakelex.textRPG.homeland.Adapters.Reputations.ReputationAdapter;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.ReputationViewModel.ReputationViewModel;
import penakelex.textRPG.homeland.databinding.FragmentReputationBinding;
//TODO: Переделать статусы в зависимости от репутации
public class ReputationFragment extends Fragment {
    private FragmentReputationBinding binding;
    private ReputationViewModel reputationViewModel;
    private final ReputationAdapter.OnReputationItemClickListener clickListener = new ReputationAdapter.OnReputationItemClickListener() {
        @Override
        public void onClickListener(ReputationItem reputationItem) {
            binding.reputationName.setText(requireActivity().getResources().getString(reputationItem.getName()));
            byte reputation = reputationItem.getReputation();
            if (reputation == 0) {
                binding.reputationTitle.setText(getResources().getString(R.string.first_meet));
            } else if (reputation >= 1 && reputation <= 25) {
                binding.reputationTitle.setText(getResources().getString(R.string.know_a_little));
            } else if (reputation > 25 && reputation <= 50) {
                binding.reputationTitle.setText(getResources().getString(R.string.well_known));
            } else {
                binding.reputationTitle.setText(getResources().getString(R.string.insider));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReputationBinding.inflate(inflater, container, false);
        reputationViewModel = new ViewModelProvider(requireActivity()).get(ReputationViewModel.class);
        reputationViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        ReputationAdapter adapter = new ReputationAdapter(clickListener);
        LiveData<List<ReputationItem>> reputations = reputationViewModel.getAllReputation();
        reputations.observe(requireActivity(), reputationItems -> {
            reputations.removeObservers(requireActivity());
            adapter.setInformation(requireActivity(), reputationItems);
        });
        binding.containerForReputations.setAdapter(adapter);
    }
}
