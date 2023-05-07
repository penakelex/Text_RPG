package penakelex.textRPG.homeland.Adapters.Inventory;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabase;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.databinding.InventoryItemBinding;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private ArrayList<InventoryItemInformation> information = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(InventoryDatabase inventoryDatabase) {
        List<InventoryItem> inventoryItems = inventoryDatabase.inventoryDao().getInventory(1);
        ArrayList<InventoryItemInformation> inventoryItemsInformation = new ArrayList<>();
        for (int i = 0; i < inventoryItems.size(); i++)
            inventoryItemsInformation.add(new InventoryItemInformation(inventoryItems.get(i).getName(), inventoryItems.get(i).getType(), inventoryItems.get(i).getPrimaryID()));
        this.information = (ArrayList<InventoryItemInformation>) inventoryItemsInformation.clone();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InventoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final InventoryItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = InventoryItemBinding.bind(itemView);
        }

        public void bind(InventoryItemInformation inventoryItemInformation) {
            binding.itemName.setText(inventoryItemInformation.getItemName());
            binding.itemType.setText(inventoryItemInformation.getType());
        }
    }
}
