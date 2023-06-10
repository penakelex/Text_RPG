package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

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

import penakelex.textRPG.homeland.Adapters.Capabilities.CapabilitiesAdapter;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.TalentsViewModel.TalentsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentCapabilitiesBinding;

public class CapabilitiesFragment extends Fragment {
    private FragmentCapabilitiesBinding binding;
    private TalentsViewModel talentsViewModel;
    private final CapabilitiesAdapter.OnCapabilityItemClickListener clickListener = new CapabilitiesAdapter.OnCapabilityItemClickListener() {
        @Override
        public void onClickListener(TalentItem talent) {
            binding.capabilityName.setText(requireActivity().getResources().getString(talent.getName()));

            switch (talent.getID()) {
                case 1 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_singer));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_singer));
                }
                case 2 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_bull));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_bull));
                }
                case 3 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_strong_kick));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_strong_kick));
                }
                case 4 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_experienced));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_experienced));
                }
                case 5 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_trained));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_trained));
                }
                case 6 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_heavyweight));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_heavyweight));
                }
                case 7 -> {
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_kind_one));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_kind_one));
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCapabilitiesBinding.inflate(inflater, container, false);
        talentsViewModel = new ViewModelProvider(requireActivity()).get(TalentsViewModel.class);
        talentsViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        CapabilitiesAdapter talentAdapter = new CapabilitiesAdapter(clickListener);
        LiveData<List<TalentItem>> talents = talentsViewModel.getHavingTalents();
        talents.observe(requireActivity(), talentItems -> {
            talents.removeObservers(requireActivity());
            talentAdapter.setInformation(requireActivity(), talentItems);
        });
        binding.containerForCapabilities.setAdapter(talentAdapter);
    }
}