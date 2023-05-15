package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Using_Volume;
import static penakelex.textRPG.homeland.Main.Constants.Using_Weight;
import static penakelex.textRPG.homeland.Main.Constants.Volume;
import static penakelex.textRPG.homeland.Main.Constants.Weight;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.Inventory.InventoryAdapter;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabase;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {
    private FragmentInventoryBinding binding;
    private InventoryDatabase inventoryDatabase;
    private InventoryAdapter inventoryAdapter;
    private SharedPreferences sharedPreferences;
    private long currentPosition = -1;
    private final InventoryAdapter.OnInventoryItemClickListener clickListener = new InventoryAdapter.OnInventoryItemClickListener() {
        @Override
        public void onClickListener(long primaryID, int ID) {
            String[] itemInformation = InventoryDatabaseHelper.getAllInventoryItemInformation(getActivity(), ID);
            binding.itemName.setText(itemInformation[0]);
            binding.itemType.setText(itemInformation[1]);
            binding.itemWeight.setText(String.format("%s %s", getResources().getString(R.string.weight), itemInformation[2]));
            binding.itemVolume.setText(String.format("%s %s", getResources().getString(R.string.volume), itemInformation[3]));
            binding.itemDescription.setText(itemInformation[4]);
            currentPosition = primaryID;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        inventoryDatabase = InventoryDatabase.getDatabase(getActivity());
        settingVolumeNWeight();
        InventoryDatabaseHelper.insertNewItemToPlayersInventory(1, getActivity());
        inventoryAdapter = new InventoryAdapter(clickListener);
        inventoryAdapter.setInformation(inventoryDatabase);
        binding.containerForItems.setAdapter(inventoryAdapter);
        binding.throwAway.setOnClickListener(listener -> throwingAwayItem());
    }

    @SuppressLint("DefaultLocale")
    private void settingVolumeNWeight() {
        binding.volume.setText(String.format("%s %d/%d", getResources().getString(R.string.volume), sharedPreferences.getInt(Using_Volume, 0), sharedPreferences.getInt(Volume, 0)));
        binding.weight.setText(String.format("%s %d/%d", getResources().getString(R.string.weight), sharedPreferences.getInt(Using_Weight, 0), sharedPreferences.getInt(Weight, 0)));
    }

    private void throwingAwayItem() {
        if (currentPosition != -1) {
            inventoryDatabase.inventoryDao().throwAwayItem(inventoryDatabase.inventoryDao().getItem(currentPosition));
            binding.itemName.setText("");
            binding.itemType.setText("");
            binding.itemDescription.setText("");
            binding.itemWeight.setText("");
            binding.itemVolume.setText("");
            currentPosition = -1;
            inventoryAdapter.setInformation(inventoryDatabase);
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_inventory_item), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
}