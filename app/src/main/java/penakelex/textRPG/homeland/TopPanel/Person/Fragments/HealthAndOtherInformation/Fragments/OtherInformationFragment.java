package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.OtherInformation.OtherInformationAdapter;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.databinding.FragmentOtherInformationBinding;

public class OtherInformationFragment extends Fragment {
    private FragmentOtherInformationBinding binding;
    private OtherInformationViewModel otherInformationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOtherInformationBinding.inflate(inflater, container, false);
        otherInformationViewModel = new ViewModelProvider(requireActivity()).get(OtherInformationViewModel.class);
        otherInformationViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        OtherInformationAdapter adapter = new OtherInformationAdapter();
        List<OtherInformationItem> otherInformation = otherInformationViewModel.getAllOtherInformation();
        adapter.setInformation(requireActivity(), otherInformation);
        binding.containerForOtherInformation.setAdapter(adapter);
    }
}
