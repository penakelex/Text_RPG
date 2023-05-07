package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Adapters.Inventory.InventoryAdapter;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabase;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {
    private FragmentInventoryBinding binding;
    private InventoryDatabase inventoryDatabase;
    private InventoryAdapter inventoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inventoryDatabase = InventoryDatabase.getDatabase(getActivity());
        inventoryAdapter = new InventoryAdapter();
        inventoryAdapter.setInformation(inventoryDatabase);
        binding.containerForInventoryItems.setAdapter(inventoryAdapter);
    }
}