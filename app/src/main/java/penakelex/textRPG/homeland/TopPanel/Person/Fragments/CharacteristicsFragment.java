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

import penakelex.textRPG.homeland.Adapters.Characteristics.CharacteristicsAdapter;
import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentCharacteristicsBinding;

public class CharacteristicsFragment extends Fragment {
    private FragmentCharacteristicsBinding binding;
    private CharacteristicsViewModel characteristicsViewModel;
    private final CharacteristicsAdapter.OnCharacteristicItemClickListener clickListener = new CharacteristicsAdapter.OnCharacteristicItemClickListener() {
        @Override
        public void onClickListener(CharacteristicItem characteristic) {
            binding.characteristicName.setText(requireActivity().getResources().getString(characteristic.getName()));
            switch (characteristic.getID()) {
                case 1 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.strength_description));
                case 2 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.physique_description));
                case 3 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.dexterity_description));
                case 4 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.mentality_description));
                case 5 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.luckiness_description));
                case 6 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.watchfulness_description));
                case 7 ->
                        binding.characteristicDescription.setText(getResources().getString(R.string.attractiveness_description));
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCharacteristicsBinding.inflate(inflater, container, false);
        characteristicsViewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter();
    }

    private void adapter() {
        CharacteristicsAdapter characteristicsAdapter = new CharacteristicsAdapter(clickListener);
        LiveData<List<CharacteristicItem>> characteristics = characteristicsViewModel.getAllCharacteristics();
        characteristics.observe(requireActivity(), characteristicItems -> {
            characteristics.removeObservers(requireActivity());
            characteristicsAdapter.setInformation(requireActivity(), characteristicItems);
        });
        binding.containerForCharacteristics.setAdapter(characteristicsAdapter);
    }
}