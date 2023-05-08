package penakelex.textRPG.homeland.TopPanel.Person.Fragments;



import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.Characteristics.CharacteristicsAdapter;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentCharacteristicsBinding;


public class CharacteristicsFragment extends Fragment {
    private FragmentCharacteristicsBinding binding;
    private final CharacteristicsAdapter.OnCharacteristicItemClickListener clickListener = new CharacteristicsAdapter.OnCharacteristicItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.characteristicName.setText(name);
            switch (position) {
                case 0:
                    binding.characteristicDescription.setText(getResources().getString(R.string.strength_description));
                    break;
                case 1:
                    binding.characteristicDescription.setText(getResources().getString(R.string.physique_description));
                    break;
                case 2:
                    binding.characteristicDescription.setText(getResources().getString(R.string.dexterity_description));
                    break;
                case 3:
                    binding.characteristicDescription.setText(getResources().getString(R.string.mentality_description));
                    break;
                case 4:
                    binding.characteristicDescription.setText(getResources().getString(R.string.luckiness_description));
                    break;
                case 5:
                    binding.characteristicDescription.setText(getResources().getString(R.string.watchfulness_description));
                    break;
                case 6:
                    binding.characteristicDescription.setText(getResources().getString(R.string.attractiveness_description));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacteristicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CharacteristicsAdapter characteristicsAdapter = new CharacteristicsAdapter(clickListener);
        characteristicsAdapter.setInformation(getActivity());
        binding.containerForCharacteristics.setAdapter(characteristicsAdapter);
    }
}