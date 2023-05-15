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

import penakelex.textRPG.homeland.Adapters.Capabilities.CapabilitiesAdapter;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentCapabilitiesBinding;

public class CapabilitiesFragment extends Fragment {
    private FragmentCapabilitiesBinding binding;
    private CapabilitiesAdapter talentAdapter;
    private SharedPreferences sharedPreferences;
    private CapabilitiesAdapter.OnCapabilityItemClickListener clickListener = new CapabilitiesAdapter.OnCapabilityItemClickListener() {
        @Override
        public void onClickListener(String name, int ID) {
            binding.capabilityName.setText(name);
            ID--;
            switch (ID) {
                case 0:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_singer));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_singer));
                    break;
                case 1:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_bull));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_bull));
                    break;
                case 2:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_strong_kick));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_strong_kick));
                    break;
                case 3:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_experienced));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_experienced));
                    break;
                case 4:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_trained));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_trained));
                    break;
                case 5:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_heavyweight));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_heavyweight));
                    break;
                case 6:
                    binding.capabilityDescription.setText(getResources().getString(R.string.description_kind_one));
                    binding.capabilityMeaning.setText(getResources().getString(R.string.meaning_kind_one));
                    break;
            }
        }
    };

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
        talentAdapter = new CapabilitiesAdapter(clickListener);
        talentAdapter.setInformation(getActivity());
        binding.containerForCapabilities.setAdapter(talentAdapter);
        talentAdapter.notifyDataSetChanged();
    }
}