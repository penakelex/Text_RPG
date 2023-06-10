package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Using_Volume;
import static penakelex.textRPG.homeland.Main.Constants.Using_Weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.Inventory.InventoryAdapter;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.InventoryViewModel.InventoryViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {
    private FragmentInventoryBinding binding;
    private InventoryTableHelper tableHelper;
    private InventoryAdapter inventoryAdapter;
    private SharedPreferences sharedPreferences;
    private InventoryItem currentInventoryItem;
    private InventoryViewModel inventoryViewModel;
    private OtherInformationViewModel otherInformationViewModel;
    private final InventoryAdapter.OnInventoryItemClickListener clickListener = new InventoryAdapter.OnInventoryItemClickListener() {
        @Override
        public void onClickListener(InventoryItem item) {
            currentInventoryItem = item;
            String[] itemInformation = tableHelper.getAllInventoryItemInformation(requireActivity(), item.getId());
            binding.itemName.setText(itemInformation[0]);
            binding.itemType.setText(itemInformation[1]);
            binding.itemWeight.setText(String.format("%s %s", getResources().getString(R.string.weight), itemInformation[2]));
            binding.itemVolume.setText(String.format("%s %s", getResources().getString(R.string.volume), itemInformation[3]));
            binding.itemDescription.setText(itemInformation[4]);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
        inventoryViewModel.initiate(requireActivity().getApplication());
        tableHelper = new InventoryTableHelper(inventoryViewModel, requireActivity());
        otherInformationViewModel = new ViewModelProvider(requireActivity()).get(OtherInformationViewModel.class);
        otherInformationViewModel.initiate(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        settingVolumeAndWeight();
        inventoryAdapter = new InventoryAdapter(clickListener, tableHelper);
        settingAdapterInformation();
        binding.containerForItems.setAdapter(inventoryAdapter);
        binding.throwAway.setOnClickListener(listener -> throwingAwayItem());
    }

    private void settingAdapterInformation() {
        LiveData<List<InventoryItem>> inventory = inventoryViewModel.getInventory((short) 1);
        inventory.observe(requireActivity(), inventoryItems -> inventoryAdapter.setInformation(requireActivity(), inventoryItems));
    }

    @SuppressLint("DefaultLocale")
    private void settingVolumeAndWeight() {
        LiveData<List<OtherInformationItem>> information = otherInformationViewModel.getAllOtherInformation();
        information.observe(requireActivity(), otherInformationItems -> {
            information.removeObservers(requireActivity());
            binding.volume.setText(String.format("%s %f/%d", getResources().getString(R.string.volume), sharedPreferences.getFloat(Using_Volume, 0), otherInformationItems.get(3).getValue()));
            binding.weight.setText(String.format("%s %f/%d", getResources().getString(R.string.weight), sharedPreferences.getFloat(Using_Weight, 0), otherInformationItems.get(2).getValue()));
        });
    }

    private void throwingAwayItem() {
        if (tableHelper.throwAwayItem(currentInventoryItem, sharedPreferences, requireContext())) {
            settingNothing();
            settingVolumeAndWeight();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_inventory_item), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingNothing() {
        binding.itemName.setText("");
        binding.itemType.setText("");
        binding.itemDescription.setText("");
        binding.itemWeight.setText("");
        binding.itemVolume.setText("");
        currentInventoryItem = null;
        inventoryAdapter.setLastPosition(-1);
    }
}