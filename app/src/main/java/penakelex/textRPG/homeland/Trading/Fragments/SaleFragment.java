package penakelex.textRPG.homeland.Trading.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Trader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import penakelex.textRPG.homeland.Adapters.TradingAdapter.TradingAdapter;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentSaleBinding;

public class SaleFragment extends Fragment {
    private FragmentSaleBinding binding;
    private TradingAdapter adapter;
    private InventoryItem item;
    SharedPreferences sharedPreferences;
    private final TradingAdapter.OnTradingItemClickListener clickListener = ID -> {
        item = InventoryDatabaseHelper.getInventoryItem(ID + 1, requireActivity().getApplicationContext());
        String[] information = InventoryDatabaseHelper.getAllInventoryItemInformation(requireActivity().getApplicationContext(), item.getId());
        binding.itemName.setText(information[0]);
        binding.itemValue.setText(String.valueOf(item.getPrice()));
        binding.itemDescription.setText(information[4]);
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TradingAdapter(clickListener);
        settingAdapterInformation();
        binding.containerForSellingItems.setAdapter(adapter);
        binding.sellItem.setOnClickListener(listener -> sellingItem());
    }

    private void settingAdapterInformation() {
        adapter.setInformation(requireActivity().getApplicationContext(), 1, false);
    }

    private void sellingItem() {
        if (item != null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            InventoryDatabaseHelper.trading(item.getPrimaryID(), sharedPreferences.getInt(Trader, 0), requireActivity().getApplicationContext());
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