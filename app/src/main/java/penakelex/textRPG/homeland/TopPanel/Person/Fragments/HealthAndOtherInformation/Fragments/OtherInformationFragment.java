package penakelex.textRPG.homeland.TopPanel.Person.Fragments.HealthAndOtherInformation.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.OtherInformation.OtherInformationAdapter;
import penakelex.textRPG.homeland.databinding.FragmentOtherInformationBinding;

public class OtherInformationFragment extends Fragment {
    private FragmentOtherInformationBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOtherInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OtherInformationAdapter adapter = new OtherInformationAdapter();
        adapter.setInformation(getActivity().getApplicationContext());
        binding.containerForOtherInformation.setAdapter(adapter);
    }
}
