package penakelex.textRPG.homeland.Trading.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Trader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.TradingAdapter.TradingAdapter;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.InventoryViewModel.InventoryViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.databinding.FragmentSaleBinding;

public class SaleFragment extends Fragment {
    private FragmentSaleBinding binding;
    private TradingAdapter adapter;
    private InventoryItem item;
    private SharedPreferences sharedPreferences;
    private InventoryTableHelper tableHelper;
    private InventoryViewModel inventoryViewModel;
    private final TradingAdapter.OnTradingItemClickListener clickListener = inventoryItem -> {
        item = inventoryItem;
        String[] information = tableHelper.getAllInventoryItemInformation(requireActivity().getApplicationContext(), item.getId());
        binding.itemName.setText(information[0]);
        binding.itemValue.setText(String.valueOf(item.getPrice()));
        binding.itemDescription.setText(information[4]);
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSaleBinding.inflate(inflater, container, false);
        inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
        inventoryViewModel.initiate(requireActivity().getApplication());
        CharacteristicsViewModel characteristicsViewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(requireActivity().getApplication());
        SkillsViewModel skillsViewModel = new ViewModelProvider(requireActivity()).get(SkillsViewModel.class);
        skillsViewModel.initiate(requireActivity().getApplication());
        tableHelper = new InventoryTableHelper(inventoryViewModel, characteristicsViewModel, skillsViewModel, requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TradingAdapter(clickListener, tableHelper);
        settingAdapterInformation();
        binding.containerForSellingItems.setAdapter(adapter);
        binding.sellItem.setOnClickListener(listener -> sellingItem());
    }

    private void settingAdapterInformation() {
        LiveData<List<InventoryItem>> inventory = inventoryViewModel.getInventory((short) 1);
        inventory.observe(requireActivity(), inventoryItems -> {
            inventory.removeObservers(requireActivity());
            adapter.setInformation(requireActivity(), inventoryItems);
        });
    }

    private void sellingItem() {
        if (item != null) {
            if (sharedPreferences == null) {
                sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            }
            tableHelper.trading(item, (short) sharedPreferences.getInt(Trader, 0));
            settingNothing();
            settingAdapterInformation();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.you_havent_chosen_item), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingNothing() {
        item = null;
        binding.itemDescription.setText("");
        binding.itemName.setText("");
        binding.itemValue.setText("");
    }
}