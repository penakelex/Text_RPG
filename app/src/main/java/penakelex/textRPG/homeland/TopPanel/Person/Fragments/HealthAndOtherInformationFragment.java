package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import penakelex.textRPG.homeland.databinding.FragmentHealthAndOtherInformationBinding;

public class HealthAndOtherInformationFragment extends Fragment {
    private FragmentHealthAndOtherInformationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthAndOtherInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}