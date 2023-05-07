package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.Talents.TalentAdapter;
import penakelex.textRPG.homeland.databinding.FragmentCapabilitiesBinding;

public class CapabilitiesFragment extends Fragment {
    private FragmentCapabilitiesBinding binding;
    private TalentAdapter talentAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCapabilitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        talentAdapter = new TalentAdapter();
        talentAdapter.setInformation(getActivity());
        binding.containerForTalents.setAdapter(talentAdapter);
        talentAdapter.notifyDataSetChanged();
    }
}