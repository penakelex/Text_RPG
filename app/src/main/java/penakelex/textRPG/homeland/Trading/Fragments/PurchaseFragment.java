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
import penakelex.textRPG.homeland.databinding.FragmentPurchaseBinding;


public class PurchaseFragment extends Fragment {
    private FragmentPurchaseBinding binding;
    private TradingAdapter adapter;
    private InventoryItem item;
    private SharedPreferences sharedPreferences;
    private InventoryTableHelper tableHelper;
    private InventoryViewModel inventoryViewModel;
    private final TradingAdapter.OnTradingItemClickListener clickListener = new TradingAdapter.OnTradingItemClickListener() {
        @Override
        public void onClickListener(InventoryItem inventoryItem) {
            item = inventoryItem;
            String[] information = tableHelper.getAllInventoryItemInformation(requireActivity().getApplicationContext(), item.getId());
            binding.itemName.setText(information[0]);
            binding.itemValue.setText(String.valueOf(item.getPrice()));
            binding.itemDescription.setText(information[4]);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);
        tableHelper();
        return binding.getRoot();
    }

    private void tableHelper() {
        inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
        inventoryViewModel.initiate(requireActivity().getApplication());
        CharacteristicsViewModel characteristicsViewModel = new ViewModelProvider(requireActivity()).get(CharacteristicsViewModel.class);
        characteristicsViewModel.initiate(requireActivity().getApplication());
        SkillsViewModel skillsViewModel = new ViewModelProvider(requireActivity()).get(SkillsViewModel.class);
        skillsViewModel.initiate(requireActivity().getApplication());
        tableHelper = new InventoryTableHelper(inventoryViewModel, characteristicsViewModel, skillsViewModel, requireActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        adapter = new TradingAdapter(clickListener, tableHelper);
        settingAdapterInformation();
        binding.containerForBuyingItems.setAdapter(adapter);
        binding.buyItem.setOnClickListener(listener -> buyingItem());
    }

    private void buyingItem() {
        if (item != null) {
            if (sharedPreferences == null) {
                sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            }
            tableHelper.trading(item, (short) 1);
            settingNothing();
            settingAdapterInformation();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.you_havent_chosen_item), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingAdapterInformation() {
        LiveData<List<InventoryItem>> inventory = inventoryViewModel.getInventory((short) sharedPreferences.getInt(Trader, 0));
        inventory.observe(requireActivity(), inventoryItems -> {
            inventory.removeObservers(requireActivity());
            adapter.setInformation(requireActivity(), inventoryItems);
        });
    }

    private void settingNothing() {
        item = null;
        binding.itemDescription.setText("");
        binding.itemName.setText("");
        binding.itemValue.setText("");
    }
}