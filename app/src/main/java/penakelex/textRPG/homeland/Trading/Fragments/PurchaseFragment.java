package penakelex.textRPG.homeland.Trading.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Trader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.TradingAdapter.TradingAdapter;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentPurchaseBinding;


public class PurchaseFragment extends Fragment {
    private FragmentPurchaseBinding binding;
    private TradingAdapter adapter;
    private InventoryItem item;
    private SharedPreferences sharedPreferences;
    private final TradingAdapter.OnTradingItemClickListener clickListener = new TradingAdapter.OnTradingItemClickListener() {
        @Override
        public void onClickListener(long ID) {
            item = InventoryDatabaseHelper.getInventoryItem(ID, requireActivity().getApplicationContext());
            Log.d("id", String.valueOf(item.getPrimaryID()));
            String[] information = InventoryDatabaseHelper.getAllInventoryItemInformation(requireActivity().getApplicationContext(), item.getId());
            binding.itemName.setText(information[0]);
            binding.itemValue.setText(String.valueOf(item.getPrice()));
            binding.itemDescription.setText(information[4]);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        adapter = new TradingAdapter(clickListener);
        settingAdapterInformation();
        binding.containerForBuyingItems.setAdapter(adapter);
        binding.buyItem.setOnClickListener(listener -> buyingItem());
    }

    private void buyingItem() {
        if (item != null) {
            sharedPreferences = requireActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
            InventoryDatabaseHelper.trading(item.getPrimaryID(), 1, requireActivity().getApplicationContext());
            settingNothing();
            settingAdapterInformation();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.you_havent_chosen_item), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingAdapterInformation() {
        adapter.setInformation(requireActivity().getApplicationContext(), sharedPreferences.getInt(Trader, 0), true);
    }

    private void settingNothing() {
        item = null;
        binding.itemDescription.setText("");
        binding.itemName.setText("");
        binding.itemValue.setText("");
    }
}