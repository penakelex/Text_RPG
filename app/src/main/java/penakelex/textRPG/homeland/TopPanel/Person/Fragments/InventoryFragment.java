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
    private long currentPosition = -1;
    private InventoryAdapter.OnInventoryItemClickListener clickListener = new InventoryAdapter.OnInventoryItemClickListener() {
        @Override
        public void onClickListener(String name, long ID, int position) {
            binding.itemName.setText(name);
            InventoryItem item = inventoryDatabase.inventoryDao().getInventory(1).get(position);
            binding.itemName.setText(item.getType());
            switch (item.getId()) {
                //TODO: Здесь сделать всякие фразочки для предметов...
            }
            currentPosition = ID;
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
        inventoryDatabase = InventoryDatabase.getDatabase(getActivity());
        inventoryDatabase.inventoryDao().insertItem(new InventoryItem("Имя", 1, "type", 1, false, 1, 10, false));
        inventoryAdapter = new InventoryAdapter(clickListener);
        inventoryAdapter.setInformation(inventoryDatabase);
        binding.containerForItems.setAdapter(inventoryAdapter);
        binding.throwAway.setOnClickListener(listener -> throwingAwayItem());
    }

    private void throwingAwayItem() {
        if (currentPosition != -1) {
            inventoryDatabase.inventoryDao().throwAwayItem(inventoryDatabase.inventoryDao().getItem(currentPosition));
            binding.itemName.setText("");
            binding.itemType.setText("");
            binding.itemDescription.setText("");
            currentPosition = -1;
            inventoryAdapter.setInformation(inventoryDatabase);
        } else {
            //TODO: Snacbar
        }
    }
}