package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.Reputations.ReputationAdapter;
import penakelex.textRPG.homeland.Databases.ReputationsDatabase.ReputationDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentReputationBinding;

public class ReputationFragment extends Fragment {
    private FragmentReputationBinding binding;
    private ReputationAdapter adapter;
    private final ReputationAdapter.OnReputationItemClickListener clickListener = new ReputationAdapter.OnReputationItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.reputationName.setText(name);
            byte reputation = ReputationDatabaseHelper.getItem(getActivity().getApplicationContext(), (short) (position + 1)).getReputation();
            if (reputation == 1) {
                binding.reputationTitle.setText(getResources().getString(R.string.first_meet));
            } else if (reputation > 1 && reputation <= 25) {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReputationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ReputationAdapter(clickListener);
        adapter.setInformation(getActivity());
        binding.containerForReputations.setAdapter(adapter);
    }
}
